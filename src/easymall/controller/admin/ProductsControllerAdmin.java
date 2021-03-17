package easymall.controller.admin;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import easymall.po.Category;
import easymall.po.Products;
import easymall.pojo.MyProducts;
import easymall.pojo.ShowProducts;
import easymall.service.ProductsService;

@Controller("productsControllerAdmin")
@RequestMapping("/admin")
public class ProductsControllerAdmin extends AdminBaseController{
	@Autowired
	private ProductsService productsService;
	@RequestMapping("/addprod")
	public String addprod(Model model) {
		//查找商品表中所有商品类别
		List<Category> categorys = productsService.allcategorys();
		model.addAttribute("categorys", categorys);
		model.addAttribute("myproducts", new MyProducts());
		return "admin/add_prod";
	}
	
	@RequestMapping("/save")
	public String save(@Valid @ModelAttribute MyProducts myproducts,HttpServletRequest request,Model model) throws Exception{
		String msg = productsService.save(myproducts,request);
		model.addAttribute("msg", msg);
		return "redirect:/admin/addprod";
	}
	
	@RequestMapping("/manageprod")
	public String manageprod(Model model){
		List<Products> allprod = productsService.allprod();
		model.addAttribute("products", allprod);
		return "admin/prod_list";
	}
	
	@RequestMapping("/delprod")
	public String delprod(String id) {
		productsService.delprod(id);
		return "admin/prod_list";
	}
	
	@RequestMapping("/updatepnum")
	public void updatepnum(String id,Integer pnum) {
		Products products = new Products();
		products.setPnum(pnum);
		products.setId(id);
		productsService.updatepnum(products);
	}
	@RequestMapping("/sold")
	public String sold(Model model){
		List<ShowProducts> products=productsService.sold();
		model.addAttribute("products", products);
		return "admin/sell_list";
	}
	@RequestMapping("/managecate")
	public String managecate(Model model){
		List<Category> allcate = productsService.allcategorys();
		model.addAttribute("allcate",allcate);
		return "admin/categorymana";
	}
	
	@RequestMapping("/delcate")
	public String delcate(String id) {
		productsService.delprod(id);
		return "admin/prod_list";
	}
	@RequestMapping("/addc")
	public String addc(Model model){
		return "admin/addcate";
	}
	@RequestMapping("/addcate")
	public String addcate(Category category,HttpSession session,Model model){
		if(category.getName()==null || category.getName()==""){
			return "admin/addcate";
		}
		if(productsService.addcate(category)>0) {
			model.addAttribute("msg", "添加成功");
		}else {
			model.addAttribute("msg", "添加失败");
		}
		return "admin/addcate";
	}
	@RequestMapping("/up")
	public String up(String id,Model model){
		Category c=productsService.up(id);
		model.addAttribute("category",c);
		return "admin/updatecate";
	}
	@RequestMapping("/upcate")
	public String upcate(Category category,Model model){
		productsService.upcate(category);
		model.addAttribute("msg","更新成功");
		return "admin/updatecate";
	}
	@RequestMapping("/showechart")
	public String showechart(Model model){
		return "admin/echarts";
	}
	@RequestMapping("/getdata")
	@ResponseBody
	public Object getdata(){
		List<ShowProducts> data=productsService.echart();
		return data;
	}
	@RequestMapping("/export")
	@ResponseBody
	public void exportExcel(HttpServletRequest request, HttpServletResponse response)     
	        throws Exception {
		List<ShowProducts> products=productsService.sold();
		XSSFWorkbook wb = new XSSFWorkbook();
		Sheet sheet=wb.createSheet("销售表");
		Row row=sheet.createRow(0);
		row.createCell(0).setCellValue("商品ID");
		row.createCell(1).setCellValue("商品名称");
		row.createCell(2).setCellValue("商品种类");
		row.createCell(3).setCellValue("商品单价");
		row.createCell(4).setCellValue("商品销量");
		for(ShowProducts p:products){
			row = sheet.createRow(row.getRowNum() + 1);
			row.createCell(0).setCellValue(p.getId());
			row.createCell(1).setCellValue(p.getName());
			row.createCell(2).setCellValue(p.getCategory());
			row.createCell(3).setCellValue(p.getPrice());
			row.createCell(4).setCellValue(p.getSoldnum());
		}
		String filename= new String("销售表.xls".getBytes(),"iso-8859-1");
		response.setContentType("application/msexcel;charset=UTF-8");
		response.setHeader("Content-disposition", "attachment;filename="+filename );
		OutputStream out=response.getOutputStream();
		wb.write(out);
		out.flush();
		out.close();
	}
	@RequestMapping("/showupprod")
	public String showupprod(Model model,String id){
		List<Category> categorys = productsService.allcategorys();
		model.addAttribute("categorys", categorys);
		Products p=productsService.oneProduct(id);
		model.addAttribute("products", p);
		return "admin/upprod";
	}
	@RequestMapping("/upprod")
	public String upprod(Products products,Model model){
		List<Category> categorys = productsService.allcategorys();
		model.addAttribute("categorys", categorys);
		productsService.upprod(products);
		model.addAttribute("msg","更新成功");
		return "admin/upprod";
	}
}
