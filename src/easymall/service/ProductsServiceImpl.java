package easymall.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import easymall.dao.ProductsDao;
import easymall.po.Category;
import easymall.po.Products;
import easymall.pojo.MyProducts;
import easymall.pojo.ShowProducts;

@Service("productsService")
public class ProductsServiceImpl implements ProductsService {
	@Autowired
	private ProductsDao productsDao;

	@Override
	public List<Category> allcategorys() {
		// TODO Auto-generated method stub
		List<Category> categorys = productsDao.allcategorys();
		return categorys;
	}

	@Override
	public List<Products> prodlist(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<Products> products = productsDao.prodlist(map);
		return products;
	}

	@Override
	public Products oneProduct(String pid) {
		// TODO Auto-generated method stub
		return productsDao.oneProduct(pid);
	}

	@Override
	public List<Products> proclass(Integer proclass) {
		// TODO Auto-generated method stub
		return productsDao.proclass(proclass);
	}

	@Override
	public String save(MyProducts myproducts, HttpServletRequest request) {
		// TODO �Զ����ɵķ������
		//1.�жϺ�׺�Ƿ�Ϸ�
		//��ȡͼ���ƣ���׺����
		String originName = myproducts.getImgurl().getOriginalFilename();
		
		//��ȡ��׺substring spilt��".png" ".jpg")
		String extName = originName.substring(originName.lastIndexOf("."));
		if(!(extName.equalsIgnoreCase(".jpg")||extName.equalsIgnoreCase(".png")||extName.equalsIgnoreCase(".gif"))) {//ͼƬ��׺���Ϸ�
			return "ͼƬ��׺���Ϸ���";
		}
		//�ж�ľ��(JAVA�����ж��Ƿ���ͼƬ���ԣ�Ҳ�������������jar���ж�)
		try {
			BufferedImage bufImage = ImageIO.read(myproducts.getImgurl().getInputStream());
			bufImage.getHeight();
			bufImage.getWidth();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			return "���ļ�����ͼƬ";
		}
		//2.����upload��ʼ��һ��·��
		//���ɶ༶·��
		String imgurl = "";
		// /a/2/e/a/2/3/j/p
		for(int i = 0;i<8;i++) {
			imgurl +="/"+ Integer.toHexString(new Random().nextInt(16));
		}
		String realpath = request.getServletContext().getRealPath("/WEB-INF");
		realpath += "/upload";
		System.out.println(realpath+imgurl);
		File file = new File(realpath+imgurl,originName);
		if(!file.exists()) {
			file.mkdirs();
		}
		//�ϴ��ļ�
		try {
			myproducts.getImgurl().transferTo(file);
		} catch (IllegalStateException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		//ƴ��ͼƬ�������ݿ��·��
		imgurl = "/upload"+imgurl +"/"+originName;
		String id = UUID.randomUUID().toString();
		Products products = new Products();
		products.setId(id);
		products.setName(myproducts.getName());
		products.setCategory(myproducts.getCategory());
		products.setPrice(myproducts.getPrice());
		products.setPnum(myproducts.getPnum());
		products.setImgurl(imgurl);
		products.setDescription(myproducts.getDescription());
		if(productsDao.findByImgurl(products.getImgurl())==null) {
			productsDao.save(products);
		}else {
			String fname = imgurl.substring(0,imgurl.lastIndexOf("."));
			imgurl=fname+System.currentTimeMillis()+extName;
			products.setImgurl(imgurl);
			System.out.println(products.getImgurl());
			productsDao.save(products);
		}
		return "��Ʒ��ӳɹ�";
	}

	@Override
	public List<Products> allprod() {
		// TODO �Զ����ɵķ������
		List<Products> allprod = productsDao.allprod();
		return allprod;
	}

	@Override
	public void delprod(String id) {
		// TODO �Զ����ɵķ������
		productsDao.delprod(id);
	}

	@Override
	public void updatepnum(Products products) {
		// TODO �Զ����ɵķ������
		productsDao.updatepnum(products);
	}

	@Override
	public List<ShowProducts> sold() {
		List<ShowProducts> products=productsDao.sold();
		return products;
	}

	@Override
	public int addcate(Category category) {
		int n=productsDao.addcate(category);
		return n;
	}

	@Override
	public Category up(String id) {
		Category c=productsDao.up(id);
		return c;
	}

	@Override
	public void upcate(Category category) {
		productsDao.upcate(category);
	}

	@Override
	public List<ShowProducts> echart() {
		List<ShowProducts> products=productsDao.echart();
		return products;
	}

	@Override
	public void upprod(Products p) {
		productsDao.upprod(p);
	}

}
