package easymall.dao.admin;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import easymall.po.admin.Admin;


@Repository("adminDao")
@Mapper
public interface AdminDao {
	public Admin tologin(Admin admin);
}
