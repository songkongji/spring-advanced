package org.example.expert.config;

import jakarta.servlet.http.HttpServletRequest;
import org.example.expert.domain.auth.exception.AuthException;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/** [클래스 설명] <br>
 * Spring MVC 의 HandlerMethodArgumentResolver 인터페이스를 구현합니다.
 * 이 클래스는 HTTP 요청에서 특정 사용자 정보를 추출하여 컨트롤러 메서드에 주입하는 역할을 합니다
 */
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {    //HandlerMethodArgumentResolver 는 컨트롤러 메서드의 인자를 해석하는 역할을 함

    /**
     * supportsParameter 메서드는 주어진 메서드 파라미터가 이 리졸버에 의해 처리될 수 있는지 여부를 결정합니다.<br>
     * 파라미터에 @Auth 어노테이션이 있는지 확인하고, 파라미터 타입이 AuthUser 클래스인지 확인합니다.<br>
     * Auth 어노테이션이 있지만 파라미터 타입이 AuthUser 가 아니거나, 그 반대의 경우 예외를 발생합니다.
     * @param parameter 컨트롤러 메서드의 인자
     * @return hasAuthAnnotation(boolean)
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAuthAnnotation = parameter.getParameterAnnotation(Auth.class) != null;
        boolean isAuthUserType = parameter.getParameterType().equals(AuthUser.class);

        // @Auth 어노테이션과 AuthUser 타입이 함께 사용되지 않은 경우 예외 발생
        if (hasAuthAnnotation != isAuthUserType) {
            throw new AuthException("@Auth와 AuthUser 타입은 함께 사용되어야 합니다.");
        }

        return hasAuthAnnotation;
    }

    @Override
    public Object resolveArgument(
            @Nullable MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest(); //NativeWebRequest 를 사용하여 HTTP 요청 객체를 가져옵니다.

        // HttpServletRequest 객체에서 JwtFilter 에서 set 한 userId, email, userRole 값을 가져옴
        Long userId = (Long) request.getAttribute("userId");
        String email = (String) request.getAttribute("email");
        UserRole userRole = UserRole.of((String) request.getAttribute("userRole"));

        return new AuthUser(userId, email, userRole);
    }
}
