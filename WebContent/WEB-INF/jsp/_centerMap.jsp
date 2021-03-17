<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style type="text/css">
    html,body{font-size:20px;}
    #allmap{ overflow: hidden;
        width: 100%;
        height: 100%;
        margin: 0;
        font-family: "微软雅黑";
        font-size:20px;}
        .BMap_bubble_title {
			 color:white;
			 font-size:13px;
			 font-weight:bold;
			 text-align:left;
			 padding-left:5px;
			 padding-top:5px;
		}
</style>
<script type="text/javascript" src="//api.map.baidu.com/api?type=webgl&v=1.0&ak=T0UGvK3qQ76Olk6SDQNdP0ellpzjasuP"></script>

  <!--百度地图容器-->
  <div id="r-result" style="width:80%;margin-left: 100px;margin-top: 10px">
        经度: <input id="longitude" type="text" style="width:200px; margin-right:10px;" />
        纬度: <input id="latitude" type="text" style="width:200px; margin-right:10px;" />
        <input type="button" value="查询" onclick="theLocation()" />
    </div>
    <div id="r-results"  style="width:80%;margin-left: 100px;margin-top: 10px"">到这去：<input type="text" id="suggestId" size="20" value="" style="width:250px;"/></div>
   <div id="searchResultPanel" style="border:1px solid #C0C0C0;width:350px;height:auto; display:none;"></div>
  <div style="width:60%;height:450px;border:#ccc solid 1px;margin-left: 350px;margin-top: 10px;" id="allmap"></div>
<script type="text/javascript">
    //创建和初始化地图函数：
    function initMap(){
        createMap();//创建地图
        setMapEvent();//设置地图事件
        addMapControl();//向地图添加控件
    }
    
    //创建地图函数：
    function createMap(){
    	var map = new BMapGL.Map("allmap");//在百度地图容器中创建一个地图
        //var point = new BMapGL.Point(113.03596858222869,23.154879254895814);//定义一个中心点坐标
        map.centerAndZoom("北京市",13);//设定地图的中心点和坐标并将地图显示在地图容器中
        //var marker = new BMapGL.Marker(point);
        //map.addOverlay(marker);
        window.map = map;//将map变量存储在全局
        //window.marker = marker;
        var geolocation = new BMapGL.Geolocation();
        geolocation.getCurrentPosition(function(r){
        	if(this.getStatus() == BMAP_STATUS_SUCCESS){
        		var mk = new BMapGL.Marker(r.point);
        		map.addOverlay(mk);
        		map.panTo(r.point);
        	}
        	else {
        		alert('failed'+this.getStatus());
        	}        
        });
    }
    
    //地图事件设置函数：
    function setMapEvent(){
        map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
        map.enableKeyboard();//启用键盘上下左右键移动地图
        
    }
    
    //地图控件添加函数：
    function addMapControl(){
		map.addEventListener("click",changeAddress);
		search();
    }

	function changeAddress(e){
		map.clearOverlays();//移除原有的 mark
		
		//拿到新的经纬度
		var pt = e.latlng;
		var lng = pt.lng;
		var lat = pt.lat;
		
		var point = new BMapGL.Point(lng, lat);
		var marker = new BMapGL.Marker(point);
		map.addOverlay(marker);
		
		
		var geoc = new BMapGL.Geocoder();
		var site = "";
		geoc.getLocation(pt, function(rs){
            //addressComponents对象可以获取到详细的地址信息
            var addComp = rs.addressComponents;
            site = addComp.province + " " + addComp.city + " " + addComp.district + " " + addComp.street + " " + addComp.streetNumber+"<br>"+"经度："+lng+"<br>"+"纬度："+lat;
    		var opts = {
            	    width: 300,
            	    height: 100,
            	    title: '当前地址'
            	};
            var infoWindow = new BMapGL.InfoWindow(site, opts);
            map.openInfoWindow(infoWindow, point); // 开启信息窗口
        }); 
	}
	
	function G(id) {
		return document.getElementById(id);
	}
	
	function search(){
		var ac = new BMapGL.Autocomplete(    //建立一个自动完成的对象
				{"input" : "suggestId"
				,"location" : map
			});

			ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
			var str = "";
				var _value = e.fromitem.value;
				var value = "";
				if (e.fromitem.index > -1) {
					value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
				}    
				str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
				
				value = "";
				if (e.toitem.index > -1) {
					_value = e.toitem.value;
					value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
				}    
				str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
				G("searchResultPanel").innerHTML = str;
			});

			var myValue;
			ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
			var _value = e.item.value;
				myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
				G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
				
				setPlace(myValue);
			});
	}

	function setPlace(myValue){
		map.clearOverlays();    //清除地图上所有覆盖物
		function myFun(){
			var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
			map.centerAndZoom(pp, 18);
			map.addOverlay(new BMapGL.Marker(pp));    //添加标注
		}
		var local = new BMapGL.LocalSearch(map, { //智能搜索
		  onSearchComplete: myFun
		});
		local.search(myValue);
	}
	
    function theLocation(){
        if(document.getElementById("longitude").value != "" && document.getElementById("latitude").value != ""){
            map.clearOverlays(); 
            var new_point = new BMapGL.Point(document.getElementById("longitude").value,document.getElementById("latitude").value);
            var marker = new BMapGL.Marker(new_point);  // 创建标注
            map.addOverlay(marker);              // 将标注添加到地图中
            map.panTo(new_point);      
        }
    }
    
    
    initMap();//创建和初始化地图
</script>