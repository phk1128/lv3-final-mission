package finalmission.global;

import finalmission.domain.Member;
import finalmission.domain.MemberRole;
import finalmission.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String SESSION_KEY = "memberId";
    private final MemberService memberService;

    public AuthInterceptor(final MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SESSION_KEY) == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "로그인이 필요합니다.");
            return false;
        }
        final Long memberId = (Long) session.getAttribute(SESSION_KEY);
        final Member member = memberService.getMemberById(memberId);
        if (MemberRole.ADMIN != member.getRole()) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "관리자 권한이 필요합니다.");
            return false;
        }
        return true;
    }
}
