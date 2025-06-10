package finalmission.service;

import finalmission.domain.Member;
import finalmission.dto.LoginRequest;
import finalmission.repository.MemberRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member getMemberById(final Long memberId) {
        return  memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없음"));
    }

    @Transactional(readOnly = true)
    public Member getMemberByLoginRequest(final LoginRequest loginRequest) {
        final Member member = memberRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));
        member.validatePassword(loginRequest.password());
        return member;
    }
}
