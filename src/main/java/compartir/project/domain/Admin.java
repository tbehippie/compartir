package compartir.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Admin {
	private Long adminId;
	private Long userId;
	
	public Admin(Long adminId, Long userId) {
		this.adminId = adminId;
		this.userId = userId;
	}
}
