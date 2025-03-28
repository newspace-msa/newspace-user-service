package com.lgcns.newspaceuserservice.service;

import com.lgcns.newspaceuserservice.dto.SignupRequestDto;
import com.lgcns.newspaceuserservice.dto.UserInfoRequestDto;
import com.lgcns.newspaceuserservice.dto.UserInfoResponseDto;
import com.lgcns.newspaceuserservice.entity.User;
import com.lgcns.newspaceuserservice.entity.UserRole;
import com.lgcns.newspaceuserservice.exception.UserException;
import com.lgcns.newspaceuserservice.exception.UserResponseStatus;
import com.lgcns.newspaceuserservice.repository.UserRepository;
import com.lgcns.newspaceuserservice.security.UserDetailsImpl;
import com.lgcns.newspaceuserservice.security.dto.JwtTokenInfo;
import com.lgcns.newspaceuserservice.security.jwt.JwtTokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService
{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenUtil jwtTokenUtil;

	/**
	 * 회원가입 서비스
	 * 
	 * @param requestDto
	 * @param bindingResult
	 * @throws MethodArgumentNotValidException
	 */
	// 회원가입
	@Transactional
	public void signup(SignupRequestDto requestDto, BindingResult bindingResult) throws MethodArgumentNotValidException
	{
		log.info("[회원가입 요청] username: {}, name: {}, nickname: {}", requestDto.getUsername(), requestDto.getName(),
				requestDto.getNickname());

		// validation 유효성 검사
		if(bindingResult.hasErrors())
		{
			throw new MethodArgumentNotValidException(null, bindingResult);
		}

		// 비밀번호, 비밀번호 확인 일치 검사
		if(!requestDto.getPassword().equals(requestDto.getPasswordConfirm()))
		{
			throw new UserException(UserResponseStatus.PASSWORD_CONFIRM_MISMATCH);
		}

		// 유저 등록
		User user = User.builder().username(requestDto.getUsername())
				.password(passwordEncoder.encode(requestDto.getPassword())).name(requestDto.getName())
				.nickname(requestDto.getNickname()).birth(requestDto.getBirth()).profileImage("")
				.userRole(UserRole.USER).build();

		log.info("[회원가입 성공] username: {}", user.getUsername());

		userRepository.save(user);
	}

	// 유저아이디 중복 체크
	@Transactional
	public boolean checkId(String username)
	{
		boolean isUserExists = userRepository.findByUsername(username).isPresent();

		if (isUserExists) {
			throw new UserException(UserResponseStatus.USERNAME_ALREADY_EXISTS);
		}

		return true;
	}

	// 회원정보 조회
	@Transactional(readOnly = true)
	public UserInfoResponseDto getUserInfo(Long userId)
	{
		User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserResponseStatus.USER_NOT_FOUND));

		return new UserInfoResponseDto(user);
	}
	
	// 회원정보 수정
	@Transactional
	public void updateUserInfo(Long userId, UserInfoRequestDto requestDto)
	{
		User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserResponseStatus.USER_NOT_FOUND));

		// 닉네임 입력하면 닉네임 변경
		if(requestDto.getNickname() != null)
		{
			user.updateNickname(requestDto.getNickname());
		}

		// 비밀번호 변경하려고 입력했으면 비밀번호 변경
		if(requestDto.getNewPassword() != null && !requestDto.getNewPassword().isEmpty()
				&& requestDto.getNewPasswordConfirm() != null && !requestDto.getNewPasswordConfirm().isEmpty())
		{
			if(!requestDto.getNewPassword().equals(requestDto.getNewPasswordConfirm()))
			{
				throw new UserException(UserResponseStatus.NEW_PASSWORD_CONFIRM_MISMATCH);
			}
			// 비밀번호 변경
			user.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));
		}
		userRepository.save(user);
	}

	/**
	 * 액세스 토큰 정보 업데이트
	 *
	 * @param user            토큰 정보를 업데이트할 유저 객체
	 * @param accessTokenInfo 업데이트할 액세스 토큰 정보
	 */
	@Transactional
	public void updateAccessToken(User user, JwtTokenInfo.AccessTokenInfo accessTokenInfo)
	{
		// 액세스 토큰 정보 업데이트
		user.updateAccessTokenInfo(accessTokenInfo);
		// 변경 사항 DB에 저장
		userRepository.save(user);
	}

	/**
	 * 리프레시 토큰 정보 업데이트 인증 / 인가에서 사용
	 * 
	 * @param user             토큰 정보를 업데이트할 유저 객체
	 * @param refreshTokenInfo 업데이트할 리프레시 토큰 정보
	 */
	@Transactional
	public void updateRefreshToken(User user, JwtTokenInfo.RefreshTokenInfo refreshTokenInfo)
	{
		// 리프레시 토큰 정보 업데이트
		user.updateRefreshTokenInfo(refreshTokenInfo);
		// 변경 사항 DB에 저장
		userRepository.save(user);
	}

	/**
	 * 액세스 토큰 기반 유저 확인
	 *
	 * @param accessToken 유저의 액세스 토큰
	 * @return 주어진 액세스 토큰을 가진 유저
	 * @throws IllegalArgumentException 주어진 액세스 토큰을 가진 유저가 없을 경우
	 */
	@Transactional(readOnly = true)
	public User findUserByAccessToken(String accessToken)
	{
		// 액세스 토큰으로 유저 찾기
		return userRepository.findUserByAccessToken(accessToken)
				.orElseThrow(() -> new UserException(UserResponseStatus.TOKEN_NOT_FOUND));
	}

	/**
	 * 리프레시 토큰 유효 여부 확인
	 * 
	 * @param refreshToken 유효 여부를 확인할 리프레시 토큰
	 * @return 리프레시 토큰의 유효 여부(유효한 경우 true, 만료된 경우 false)
	 */
	@Transactional(readOnly = true)
	public boolean isRefreshTokenValid(String refreshToken)
	{
		// 리프레시 토큰의 만료 시간 확인
        LocalDateTime refreshTokenExpirationTime = userRepository
                .findRefreshTokenExpirationTimeByRefreshToken(refreshToken);
		// 현재 시간과 비교하여 유효 여부 반환
		return !refreshTokenExpirationTime.isBefore(LocalDateTime.now());
	}

	@Transactional
	public Map<String, String> updateProfileImage(UserDetailsImpl userDetails, String absoluteFilePath) throws Exception
	{
		User user = userDetails.getUser();
		Map<String, String> result = new HashMap<>();
		try
		{
			String relativePath = "";
			if(!absoluteFilePath.equals(""))
				relativePath = absoluteFilePath
				.substring(absoluteFilePath.indexOf("/uploads") + "/uploads".length());
			// user에 ProfileImage 경로 저장
			user.updateProfileImage(relativePath);
			userRepository.save(user);
			result.put("message", "프로필 이미지 수정 성공");
			result.put("file", relativePath);
			return result;
		}
		catch(Exception e)
		{
			result.put("message", "프로필 이미지 수정 실패");
			result.put("description", e.getMessage());
			return result;
		}
	}

	@Transactional
	public Resource getImageResource(String uploadDir, UserDetailsImpl userDetails) throws Exception
	{
		User user = userDetails.getUser();
		String profileImage = user.getProfileImage();
		if(profileImage == null) profileImage = "";
		Path imagePath = Paths.get(uploadDir + profileImage);
		Resource resource = new UrlResource(imagePath.toUri());
		return resource;
	}

	@Transactional
	public void logoutUser(HttpServletResponse response, User user)
	{
		// 쿠키에서 토큰을 삭제
		Cookie cookie = jwtTokenUtil.removeTokenCookie();
		// 빈 쿠키를 응답으로 반환하기
		response.addCookie(cookie);
		// 액세스 토큰 초기화 (여러 유저가 같은 아이디 사용할때 로그아웃 테스트하면서 문제가 발생하는 것 같아서 임시 주석)
		// 토큰들 만료시키기
		user.setTokenExpirationTime(LocalDateTime.now());
		userRepository.save(user);
	}

	@Transactional
	// 회원탈퇴
	public void deleteUser(HttpServletResponse response, User user) throws Exception
	{
		// 현재 userDetails에 인증된 userId로
		// userService에서 구현된 회원탈퇴 메서드를 수행한다.
		// 일반적으로 쿠키를 먼저 삭제하고, 유저 db를 삭제해야함
		// db가 먼저 삭제될시 쿠키를 삭제할수 없다.
		logoutUser(response, user);
		userRepository.deleteById(user.getId()); // 리포지토리에서 사용자 삭제
	}

}
