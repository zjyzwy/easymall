package easymall.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import easymall.po.Category;
import easymall.po.Products;
import easymall.pojo.MyProducts;
import easymall.pojo.ShowProducts;

public interface ProductsService {
	//������Ʒ���
	public List<Category> allcategorys();
	//������Ʒ
	public List<Products> prodlist(Map<String,Object> map);
	//����pid���ҵ�����Ʒ
	public Products oneProduct(String pid);
	//���ݷ��������Ʒ
	public List<Products> proclass(Integer proclass);
	//�����Ʒ
	public String save(MyProducts myproducts, HttpServletRequest request);
	//����������Ʒ
	public List<Products> allprod();
	public void delprod(String id);
	public void updatepnum(Products products);
	public List<ShowProducts> sold();
	public int addcate(Category category);
	public Category up(String id);
	public void upcate(Category category);
	public List<ShowProducts> echart();
	public void upprod(Products p);
}
