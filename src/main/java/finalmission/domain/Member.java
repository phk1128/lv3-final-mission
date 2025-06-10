package finalmission.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.proxy.HibernateProxy;

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
            throw new IllegalArgumentException("패스워드가 일치 하지 않습니다.");
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
