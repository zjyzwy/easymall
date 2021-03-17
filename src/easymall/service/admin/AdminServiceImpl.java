package easymall.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import easymall.dao.admin.AdminDao;
import easymall.po.admin.Admin;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminDao adminDao;

	@Override
	public Admin tologin(Admin admin) {
		// TODO 自动生成的方法存根
		return adminDao.tologin(admin);
	}

}
