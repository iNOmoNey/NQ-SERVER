package top.theanything.web.pojo;

/**
 * @author zhou
 * @Description
 * @createTime 2020-05-04
 */
public class User {
	private String name;
	private Integer age;
	private String sex;
	private String hobby;

	public User() {
	}
	public User(String name,  Integer age, String sex , String hobby) {
		this.name = name;
		this.hobby = hobby;
		this.age = age;
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}
