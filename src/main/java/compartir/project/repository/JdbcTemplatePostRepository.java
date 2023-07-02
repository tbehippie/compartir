package compartir.project.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import compartir.project.domain.Post;
import compartir.project.domain.PostUserInfo;


@Repository
public class JdbcTemplatePostRepository implements PostRepository {
	private final JdbcTemplate jdbcTemplate;
	
	public JdbcTemplatePostRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private RowMapper<Post> postRowMapper(){
		return new RowMapper<Post>() {

			@Override
			public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
				Post post = new Post();
				post.setPostId(rs.getLong("post_id"));
				post.setPostImage(rs.getString("post_image"));
				post.setPostContent(rs.getString("post_content"));
				post.setUserId(rs.getLong("user_id"));
				post.setDate(rs.getString("date"));
				return post;
			}
		};
	}
	
	private RowMapper<PostUserInfo> postInfoRowMapper()	{
		return (ResultSet rs, int rowNum) -> {
			PostUserInfo info = new PostUserInfo();
			info.setPostId(rs.getLong("post_id"));
			info.setUserId(rs.getLong("user_id"));
			info.setUserName(rs.getString("user_name"));
			info.setProfile(rs.getString("profile"));
			return info;
		};
	}
	
	@Override
	public Post save(Post post) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("post")
				.usingGeneratedKeyColumns("post_id");
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("Post_image", post.getPostImage());
		parameters.put("post_content", post.getPostContent());
		parameters.put("user_id", post.getUserId());
		parameters.put("date", post.getDate());
		
		Number postId = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		post.setPostId(postId.longValue());
		
		return post;
	}

	@Override
	public void edit(Post post) {
		String sql = "update post set post_image = ?, post_content = ?, user_id = ?, date = ? where post_Id = ?";
		jdbcTemplate.update(sql, post.getPostImage(), post.getPostContent(), post.getUserId(), post.getDate(), post.getPostId());
	}

	@Override
	public Post findById(Long postId) {
		String sql = "select * from post where post_id = ?";
		Post foundPost = jdbcTemplate.query(sql, postRowMapper(), postId)
				.stream()
				.findFirst()
				.get();
		
		return foundPost;
	}

	@Override
	public void delete(Long postId) {
		String sql = "delete from post where post_id = ?";
		jdbcTemplate.update(sql, postId);
		
	}

	@Override
	public List<Post> findAll() {
		return jdbcTemplate.query("select * from post order by post_id desc;", postRowMapper());
	}

	@Override
	public List<Post> findAllByUserId(Long userId) {
		return jdbcTemplate.query("select * from post where user_id = ?", postRowMapper(), userId);
	}

	@Override
	public List<PostUserInfo> findPostInfo() {
		return jdbcTemplate.query("select a.user_id, a.user_name, a.profile, b.post_id FROM users a JOIN post b on a.user_id = b.user_id", postInfoRowMapper());
	} 
}
