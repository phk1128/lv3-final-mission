package finalmission.domain;

import finalmission.exception.BadRequestException;
import finalmission.exception.ErrorCode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    public void validatePassword(final String password) {
        if (!Objects.equals(password, this.password)) {
            throw new BadRequestException(ErrorCode.INVALID_PASSWORD);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Member member)) {
            return false;
        }
        return Objects.equals(getId(), member.getId()) && Objects.equals(getEmail(), member.getEmail())
                && Objects.equals(getPassword(), member.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPassword());
    }
}
