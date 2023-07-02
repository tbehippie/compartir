package compartir.project.repository;

import java.util.List;
import java.util.Optional;

import compartir.project.domain.Noti;

public interface NotiRepository {
	
	Noti create(Noti noti); // 공지 추가
	List<Noti> findAll(); // 공지 전체조회
	Optional<Noti> findById(Long notiId); // 개별공지 조회 - notiId로
	Optional<Noti> findByName(String notiTitle); // 개별공지 조회 - notiTitle로
	Noti update(Long notiId, Noti updateNoti); // 공지 수정
	void deleteById(Long notiId); // 공지 삭제
	
}