package compartir.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import compartir.project.domain.Noti;
import compartir.project.repository.NotiRepository;

@Service
public class NotiService {

	private final NotiRepository repository;
	
	public NotiService(NotiRepository repository) {
		this.repository = repository;
	}
	
	//공지 전체찾기
	public List<Noti> findAll() {
		return repository.findAll();
	}
	
	//아이디로  조회
	public Optional<Noti> findById(Long notiId) {
		return repository.findById(notiId);
	}
	
	//제목으로 조회
	public Optional<Noti> findByName(String notiTitle) {
		return repository.findByName(notiTitle);
	}

	// 수정
	public Noti update(Long notiId, Noti updateNoti) {
		return repository.update(notiId, updateNoti); 
	}
	
	// 삭제
	public void deleteById(Long notiId) {
		repository.deleteById(notiId);
	}
	
	// 생성
	public void create(Noti noti) {
		repository.create(noti);
	}

}
