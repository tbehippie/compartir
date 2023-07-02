package compartir.project.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Noti {
	private Long notiId;
	private String notiTitle;
	private String notiContent;
	private Long adminId;

	public Noti() {
	}
	
	public Noti(String notiTitle, String notiContent, Long adminId) {
		this.notiTitle = notiTitle;
		this.notiContent = notiContent;
		this.adminId = adminId;
	}

}
