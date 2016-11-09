package loki.ca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import loki.ca.model.Course;
import loki.ca.model.Student;
import loki.ca.model.University;

@Repository
public class StudentDaoImpl implements StudentDao {

	@Autowired
	private UserDao userDao;

	private static String SQL_SELECT_STUDENT = "SELECT u.id AS u_id, u.email, u.password, u.first_name, u.second_name, u.last_name, u.is_active, af.id AS af_id, "
			+ "af.photo_scope AS af_photo, c.id AS c_id, c.title AS c_title, un.id as un_id, un.title AS un_title, un.description AS un_desc,  un.city AS un_city "
			+ "FROM users AS u INNER JOIN application_forms AS af ON u.id=af.user_id INNER JOIN courses AS c ON af.course_id=c.id "
			+ "INNER JOIN universities AS un ON af.university_id=un.id";
	private static String SQL_SELECT_STUDENT_BY_ID = SQL_SELECT_STUDENT + " WHERE u.id=?";
	private static String SQL_SELECT_STUDENT_BY_APP_FORM_ID = SQL_SELECT_STUDENT +  " WHERE af.id=?";
	private static String SQL_INSERT_APP_FORM = "INSERT INTO application_forms (course_id, university_id, photo_scope, user_id) VALUES (?, ?, ?, ?)";
	private static String SQL_UPDATE_APP_FORM = "UPDATE application_forms SET course_id=?, university_id=?, photo_scope=?, user_id=? WHERE id=?";
	/*private static String SQL_SELECT_STUDENTS_BY_PROJECT = "SELECT u.id AS u_id, u.email, u.first_name, u.second_name, u.last_name, u.is_active, af.id AS af_id, "
			+ "af.photo_scope AS af_photo, af.course_id AS c_id, af.university_id as un_id, FROM users AS u INNER JOIN application_forms AS af ON u.id=af.user_id "
			+ "INNER JOIN students_in_project AS sp ON af.id=sp.app_form_id INNER JOIN statuses_in_project AS spst ON sp.id=spst.student_in_project_id "
			+ "INNER JOIN student_in_project_status_types AS st ON spst.status_in_project_id=st.id "
			+ "WHERE spst.datetime = (SELECT MAX(datetime) FROM statuses_in_project WHERE student_in_project_id=sp.id) AND sp.project_id=?";*/

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Student getStudentById(int id) {
		return jdbcTemplate.queryForObject(SQL_SELECT_STUDENT_BY_ID, new StudentRowMapper(), id);
	}

	public Student getStudentByAppFormId(int appFormId) {
		return jdbcTemplate.queryForObject(SQL_SELECT_STUDENT_BY_APP_FORM_ID, new StudentRowMapper(), appFormId);
	}

	public void addStudent(final Student student) {
		userDao.addUser(student);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(SQL_INSERT_APP_FORM, new String[] { "id" });
				ps.setInt(1, student.getCourse().getId());
				ps.setInt(2, student.getUniversity().getId());
				ps.setString(3, student.getPhotoSrc());
				ps.setInt(4, student.getId());
				return ps;
			}
		}, keyHolder);
		student.setAppFormId(keyHolder.getKey().intValue());
	}

	public void updateStudent(Student student) {
		userDao.updateUser(student);
		jdbcTemplate.update(SQL_UPDATE_APP_FORM, student.getCourse().getId(), student.getUniversity().getId(),
				student.getPhotoSrc(), student.getId(), student.getAppFormId());

	}

	/*public List<Student> getStudentsByProject(Project project) {
		return jdbcTemplate.query(SQL_SELECT_STUDENTS_BY_PROJECT, new RowMapper<Student>() {

			public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
				Student student = new Student();
				student.setId(rs.getInt("u_id"));
				student.setEmail(rs.getString("email"));
				student.setFirstName(rs.getString("first_name"));
				student.setSecondName(rs.getString("second_name"));
				student.setLastName(rs.getString("last_name"));
				student.setActive(rs.getBoolean("is_active"));
				student.setAppFormId(rs.getInt("af_id"));
				Course course = new Course();
				course.setId(rs.getInt("c_id"));
				student.setCourse(course);
				University university = new University();
				university.setId(rs.getInt("un_id"));
				student.setUniversity(university);
				student.setPhotoSrc(rs.getString("af_photo"));
				return student;
			}
			
		});
	}*/

	private static class StudentRowMapper implements RowMapper<Student> {

		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			Student student = new Student();
			student.setId(rs.getInt("u_id"));
			student.setEmail(rs.getString("email"));
			student.setPassword(rs.getString("password"));
			student.setFirstName(rs.getString("first_name"));
			student.setSecondName(rs.getString("second_name"));
			student.setLastName(rs.getString("last_name"));
			student.setActive(rs.getBoolean("is_active"));
			student.setAppFormId(rs.getInt("af_id"));
			Course course = new Course();
			course.setId(rs.getInt("c_id"));
			course.setTitle(rs.getString("c_title"));
			student.setCourse(course);
			University university = new University();
			university.setId(rs.getInt("un_id"));
			// TODO set university parameters
			student.setUniversity(university);
			student.setPhotoSrc(rs.getString("af_photo"));
			return student;
		}

	}

}