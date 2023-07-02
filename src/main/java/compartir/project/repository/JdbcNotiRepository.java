package compartir.project.repository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import compartir.project.domain.Noti;

@Repository
public class JdbcNotiRepository implements NotiRepository {

	private final JdbcTemplate jdbcTemplate;

	// 생성자
	//@Autowired
	public JdbcNotiRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper<Noti> notiRowMapper() {
		return (rs, rowNum) -> {
			Noti noti = new Noti();
			noti.setNotiId(rs.getLong("noti_id"));
			noti.setNotiTitle(rs.getString("noti_title"));
			noti.setNotiContent(rs.getString("noti_content"));
			return noti;
		};
	}

	//공지사항 생성
	@Override
	public Noti create(Noti noti) {
		
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("notification")
				.usingGeneratedKeyColumns("noti_id");
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("noti_title", noti.getNotiTitle());
		parameters.put("noti_content", noti.getNotiContent());
		parameters.put("admin_id", 1);//
		
		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		
		noti.setNotiId(key.longValue());
		return noti;
	}
	
	// 공지사항 전체 보기
	@Override
	public List<Noti> findAll() {
		return jdbcTemplate.query("select * from notification", notiRowMapper());
	}
	
	//공지 조회 - Id
	@Override
	public Optional<Noti> findById(Long notiId) {
		String query = "select * from notification where noti_id = ?";
		List<Noti> result = jdbcTemplate.query(query, notiRowMapper(), notiId);
		return result.stream().findAny();
	}
	
	//공지 조회	- Name
	public Optional<Noti> findByName(String notiTitle) {
		String sql = "select * from notification where noti_content like ?";
		List<Noti> result = jdbcTemplate.query(sql, notiRowMapper(), notiTitle);
		 return result.stream().findAny();
	}
		
	// 공지사항 수정
	@Override
	public Noti update(Long notiId, Noti updateNoti) {
		String sql = "update notification set noti_title = ?, noti_content = ? where noti_id = ?";
		int result = jdbcTemplate.update(sql, updateNoti.getNotiTitle(), updateNoti.getNotiContent(), notiId);
		return findById(notiId).get();
	}

	// 공지사항 삭제 
	@Override
	public void deleteById(Long notiId) {
		jdbcTemplate.update("delete from notification where noti_id = ?", notiId);
	}

}
