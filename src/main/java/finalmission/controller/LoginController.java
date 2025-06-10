package finalmission.controller;

import finalmission.domain.Member;
import finalmission.dto.LoginRequest;
import finalmission.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody final LoginRequest loginRequest, final HttpSession session) {
        final Member member = memberService.getMemberByLoginRequest(loginRequest);
        session.setAttribute("memberId", member.getId());
        session.setMaxInactiveInterval(60 * 60);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
