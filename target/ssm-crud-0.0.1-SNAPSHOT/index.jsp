<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>

<%
    pageContext.setAttribute("APP_PATH", request.getContextPath());
%>

<!-- web路径：
	不以/开始的相对路径，找资壖，以当前资源的路径为基准，容易出现问题
	以/开始的相对路径，资源以服务器的路径为标准
	比如(http://localhost:3360):需要加上项目名
	http://localhost:3306/crud
 -->
<script type="text/javascript"
	src="${APP_PATH}/static/js/jquery-3.3.1.min.js"></script>
<link
	href="${APP_PATH}/static/bootstrap-3.3.7-dist//css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
	
	<!-- 员工添加的模态框 bootstrap的水平模态框 -->
	<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">员工添加</h4>
				</div>
				<div class="modal-body">

					<!-- 模态框中添加表单 -->
					<form class="form-horizontal">
						<!-- 表单中第一项 姓名 -->
						<div class="form-group">
							<label class="col-sm-2 control-label">empName</label>
							<div class="col-sm-10">
								<input type="text" name="empName" class="form-control"
									id="empName_add_input" placeholder="empName"> <span
									id="helpBlock2" class="help-block"></span>
							</div>
						</div>
						<!-- 表单中第二项 邮件 -->
						<div class="form-group">
							<label class="col-sm-2 control-label">email</label>
							<div class="col-sm-10">
								<input type="text" name="email" class="form-control"
									id="email_add_input" placeholder="email@gmail.com"> <span
									id="helpBlock2" class="help-block"></span>
							</div>
						</div>
						<!-- 表单中第三项性别 -->
						<div class="form-group">
							<label class="col-sm-2 control-label">gender</label>
							<div class="col-sm-10">
								<label class="radio-inline"> <input type="radio"
									name="gender" id="gender1_add_input" value="M"
									checked="checked"> 男
								</label> <label class="radio-inline"> <input type="radio"
									name="gender" id="gender2_add_input" value="F"> 女
								</label>
							</div>
						</div>
						<!-- 表单中第4项 部门 -->
						<div class="form-group">
							<label class="col-sm-2 control-label">deptName</label>
							<div class="col-sm-4">
								<!-- 部门提交部门ID即可 ，不写死，部门选择用js控制-->
								<select class="form-control" name="dId">
								</select>
							</div>
						</div>

					</form>
					<!-- 关闭，保存按钮 -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 员工修改的模态框 -->
	<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">员工修改</h4>
				</div>
				<div class="modal-body">

					<!-- 模态框中添加表单 -->
					<form class="form-horizontal">
						<!-- 表单中第一项 姓名 -->
						<div class="form-group">
							<label class="col-sm-2 control-label">empName</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="empName_update_static"></p>
								<span id="helpBlock2" class="help-block"></span>
							</div>
						</div>
						<!-- 表单中第二项 邮件 -->
						<div class="form-group">
							<label class="col-sm-2 control-label">email</label>
							<div class="col-sm-10">
								<input type="text" name="email" class="form-control"
									id="email_update_input" placeholder="email@gmail.com">
								<span id="helpBlock2" class="help-block"></span>
							</div>
						</div>
						<!-- 表单中第三项性别 -->
						<div class="form-group">
							<label class="col-sm-2 control-label">gender</label>
							<div class="col-sm-10">
								<label class="radio-inline"> <input type="radio"
									name="gender" id="gender1_update_input" value="M"
									checked="checked"> 男
								</label> <label class="radio-inline"> <input type="radio"
									name="gender" id="gender2_update_input" value="F"> 女
								</label>
							</div>
						</div>
						<!-- 表单中第4项 部门 -->
						<div class="form-group">
							<label class="col-sm-2 control-label">deptName</label>
							<div class="col-sm-4">
								<!-- 部门提交部门ID即可 ，不写死，部门选择用js控制-->
								<select class="form-control" name="dId">
								</select>
							</div>
						</div>

					</form>
					<!-- 关闭，保存按钮 -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="emp_update_btn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题行 -->
		<div class="row">
			<div class="col-md-12">
				<h1>SSM-CRUD</h1>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-8">
				<button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
				<button class="btn btn-danger" id="emp_delete_all_btn">删除</button>
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover" id="emps_table">
					<thead>
						<tr>
							<th><input type="checkbox" id="check_all" /></th>
							<th>#</th>
							<th>empName</th>
							<th>gender</th>
							<th>email</th>
							<th>deptName</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</div>
		</div>
		<!-- 显示分页信息 -->
		<div class="row">
			<!-- 分页文字信息 -->
			<div class="col-md-6" id="page_info_area"></div>
			<!-- 分页条信息 -->
			<div class="col-md-6" id="page_nav_area"></div>
		</div>
	</div>
	
	<script type="text/javascript">
		var totalRecorder, totalPages, currentPage;

		//1. 页面加载完成之后，直接去发送ajax请求，要到分页数据
		$(function() {
			//去首页
			to_page(1);
		});

		function to_page(pn) {
			$.ajax({
				url : "${APP_PATH}/emps",
				data : "pn=" + pn,
				type : "GET",
				success : function(result) {
					//console.log(result);
					//1.解析并显示员工数据
					build_emps_table(result);
					//2.解析并显示分页信息
					build_page_info(result);
					//3.解析显示分页条数据
					build_page_nav(result);
				}
			});
		}
		function build_emps_table(result) {
			//首先清空之前缓存的数据
			$("#emps_table tbody").empty();
			var emps = result.extend.pageInfo.list;
			$.each(emps, function(index, item) {
				//alert(item.empName);
				var checkBoxTd = $("<td><input type='checkbox' class='check_item'></input></td>");
				var empIdTd = $("<td></td>").append(item.empId);
				var empNameTd = $("<td></td>").append(item.empName);
				var genderTd = $("<td></td>").append(
						item.gender == "M" ? "男" : "女");
				var emailTd = $("<td></td>").append(item.email);
				var deptNameTd = $("<td></td>")
						.append(item.department.deptName);
				/**
				<button type="button" class="btn btn-primary btn-sm">
								修改 <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
							</button>
				 */
				var editBtn = $("<button></button>").addClass(
						"btn btn-primary btn-sm edit_btn").append(
						$("<span></span>").addClass(
								"glyphicon glyphicon-pencil")).append("编辑");
				//为编辑按钮添加属性以记录其员工id
				editBtn.attr("edit-id",item.empId);
				var delBtn = $("<button></button>").addClass(
						"btn btn-danger btn-sm delete_btn").append(
						$("<span></span>")
								.addClass("glyphicon glyphicon-trash")).append(
						"删除");
				delBtn.attr("del-id",item.empId);
				var btnTd = $("<td></td>").append(editBtn).append(" ").append(
						delBtn);
				//append方法执行完成后还是返回原来的元素
				$("<tr></tr>").append(checkBoxTd).append(empIdTd).append(empNameTd).append(
						genderTd).append(emailTd).append(deptNameTd).append(
						btnTd).appendTo("#emps_table tbody");
			});
		}
		function build_page_info(result) {
			//首先清空数据
			$("#page_info_area").empty();
			$("#page_info_area").append(
					"当前第 " + result.extend.pageInfo.pageNum + " 页，共 "
							+ result.extend.pageInfo.pages + " 页，总 "
							+ result.extend.pageInfo.total + "条记录");
			totalRecorder = result.extend.pageInfo.total;
			totalPages = result.extend.pageInfo.pages;
			currentPage = result.extend.pageInfo.pageNum;
		}
		function build_page_nav(result) {
			$("#page_nav_area").empty()
			//page_nav_area
			var ul = $("<ul></ul>").addClass("pagination");
			//构建首页前页元素
			var firstPageLi = $("<li></li>").append(
					$("<a></a>").append("首页").attr("href", "#"));
			var prePageLi = $("<li></li>").append(
					$("<a></a>").append("&laquo;"));
			if (result.extend.pageInfo.hasPreviousPage == false) {
				prePageLi.addClass("disabled");
				firstPageLi.addClass("disabled");
			} else {
				//为元素添加点击翻页的事件
				firstPageLi.click(function() {
					to_page(1);
				});
				prePageLi.click(function() {
					to_page(result.extend.pageInfo.pageNum - 1);
				});
			}
			//构建末页后页元素
			var nextPageLi = $("<li></li>").append(
					$("<a></a>").append("&raquo;"));
			var lastPageLi = $("<li></li>").append(
					$("<a></a>").append("末页").attr("href", "#"));
			if (result.extend.pageInfo.hasNextPage == false) {
				nextPageLi.addClass("disabled");
				lastPageLi.addClass("disabled");
			} else {
				//为元素添加点击翻页的事件
				lastPageLi.click(function() {
					to_page(result.extend.pageInfo.pages);
				});
				nextPageLi.click(function() {
					to_page(result.extend.pageInfo.pageNum + 1);
				});
			}
			//添加首页和前一页
			ul.append(firstPageLi).append(prePageLi);
			//1,2,3……遍历给ul中添加页码
			$.each(result.extend.pageInfo.navigatepageNums, function(index,
					item) {
				var numLi = $("<li></li>").append($("<a></a>").append(item));
				if (result.extend.pageInfo.pageNum == item) {
					numLi.addClass("active");
				}
				numLi.click(function() {
					to_page(item);
				});
				ul.append(numLi);
			});
			//添加下一页和末页提示
			ul.append(nextPageLi).append(lastPageLi);
			var navEle = $("<nav></nav>").append(ul);
			navEle.appendTo("#page_nav_area");
		}

		//点击新增按钮弹出模态框
		$("#emp_add_modal_btn").click(function() {
			//清除上次的表单数据(表单完整充值：表单的数据，表单的样式)
			reset_form("#empAddModal form");
			//$("#empAddModal form")[0].reset();
			//发送ajax请求，查出部门信息，显示在下拉列表中
			getDepts("#empAddModal select"); 
			//getDepts();
			//弹出模态框
			$("#empAddModal").modal({
				backdrop : 'static'
			});
		});

		//查出所有部门信息并显示在下拉列表
		function getDepts(ele) {
			//清空之前下拉列表的值
			$(ele).empty(); 
			$.ajax({
				url : "${APP_PATH}/depts",
				type : "GET",
				success : function(result) {
					//{"code":100,"msg":"处理成功","
					//extend":{"depts":[{"deptId":1,"deptName":"开发部"}]}}
					/* console.log(result); */
					//显示部门信息在下拉列表中
					//$("#dept_add_select")可以不用select的id直接在模态框中找，因为模态框中就一个select
					//$("#empAddModal select").append()
					$.each(result.extend.depts, function() {
						var optionEle = $("<option></option>").append(
								this.deptName).attr("value", this.deptId);
						optionEle.appendTo(ele);
					});

				}
			});
		}

		$("#emp_save_btn")
				.click(
						function() {
							//将1.2步调换顺序，解决前端校验覆盖后端用户重复的校验显示问题
							//2.对输入的名字进行校验，判断姓名是否已经存在，在上面对用户名进行是否重复时，如果重复给
							//保存按钮添加一个自定义属性，然后这里判断是否有这个属性来判断保存按钮是否可用
							if ($(this).attr("ajax-va") == "error") {
								return false;
							}
							//1.先对要提交给服务器的数据进行校验,判断是否符合格式
							if (!validate_add_form()) {
								return false;
							} 
							$.ajax({
										url : "${APP_PATH}/emp",
										type : "POST",
										data : $("#empAddModal form")
												.serialize(),
										success : function(result) {
											//alert(result.msg);
											//后台校验传回的数据
											if (result.code == 100) {
												//员工保存成功
												//1.关闭模态框
												$("#empAddModal").modal("hide");
												//2.来到最后一页，显示刚才保存的数据
												//发送ajax请求显示最后一页数据即可
												//使用pageHelper的特点，当请求的页数大于最大页数时，返回最大页数
												//totalRecorder全局变量记录总条数一定大于最大页数
												to_page(totalRecorder);
												//to_page(totalPages + 1);
											} else {
												//显示失败信息
												//console.log(result);
												//有哪个字段的错误信息就显示哪个字段的
												if (undefined != result.extend.errorFields.email) {
													//显示邮箱错误信息
													show_validate_msg(
															"#email_add_input",
															"error",
															result.extend.errorFields.email);
												}
												if (undefined != result.extend.errorFields.empName) {
													//显示员工名字错误信息
													show_validate_msg(
															"#empName_add_input",
															"error",
															result.extend.errorFields.empName);
												}
											}
										}
									});
						});

		//校验表单数据格式是否符合
		function validate_add_form() {
			//1.校验用户名，用正则表达式
			var empName = $("#empName_add_input").val();
			//alert(empName);
			var regName = /(^[A-Za-z0-9]{6,16}$)|(^[\u2E80-\u9FFF]{2,5}$)/;
			if (!regName.test(empName)) {
				//alert("用户名可以是2-5位中文或者6-16位英文和数字组合");
				show_validate_msg("#empName_add_input", "error",
						"用户名必须是2-5位中文或者6-16位英文和数字组合");
				//$("#empName_add_input").parent().addClass("has-error");
				//$("#empName_add_input").next("span").text("用户名可以是2-5位中文或者6-16位英文和数字组合");
				return false;
			} else {
				show_validate_msg("#empName_add_input", "success", "");
				//$("#empName_add_input").parent().addClass("has-success");
				//$("#empName_add_input").next("span").text("");
			}
			//2.校验邮箱
			var email = $("#email_add_input").val();
			var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if (!regEmail.test(email)) {
				//alert("邮箱格式不正确");
				show_validate_msg("#email_add_input", "error", "邮箱格式不正确");
				//$("#email_add_input").parent().addClass("has-error");
				//$("#email_add_input").next("span").text("邮箱格式不正确");
				return false;
			} else {
				show_validate_msg("#email_add_input", "success", "");
				//$("#email_add_input").parent().addClass("has-success");
				//$("#email_add_input").next("span").text("");
			}
			return true;
		}

		//显示校验结果的提示信息
		function show_validate_msg(ele, status, msg) {
			//清除当前元素的校验状态
			$(ele).parent().removeClass("has-success has-error");
			$(ele).next("span").text("");
			if ("success" == status) {
				$(ele).parent().addClass("has-success");
				$(ele).next("span").text(msg);
			} else if ("error" == status) {
				$(ele).parent().addClass("has-error");
				$(ele).next("span").text(msg);
			}
		}
		//表单内容重置
		function reset_form(ele) {
			//清空表单数据
			$(ele)[0].reset();
			//清空表单样式
			$(ele).find("*").removeClass("has-error has-success");
			$(ele).find(".help-block").text("");
		}

		//员工姓名是否重复的校验
		$("#empName_add_input").change(
				function() {
					//发送ajax校验用户名是否可用
					var empName = this.value;
					//alert(empName);
					$.ajax({
						url : "${APP_PATH}/checkuser",
						type : "POST",
						data : "empName=" + empName,
						success : function(result) {
							if (result.code == 100) {
								show_validate_msg("#empName_add_input",
										"success", result.extend.va_msg);
								$("#emp_save_btn").attr("ajax-va", "success");
							} else {
								show_validate_msg("#empName_add_input",
										"error", result.extend.va_msg);
								$("#emp_save_btn").attr("ajax-va", "error");
							}
						}
					});
				});
		
		//1.我们是按钮创建之前就绑定了click，所以绑定不上
		//1>,可以在创建按钮的时候绑定    2>,绑定点击.live
		//jquery新版本没有live方法，使用on方法替代,直接用on不行，on绑在后代上
		$(document).on("click", ".edit_btn", function() {
			//1.查出部门信息，显示部门列表
			getDepts("#empUpdateModal select");
			//2.查出员工信息，显示员工信息
			getEmp($(this).attr("edit-id"));
			//3.把员工ID传递给模态框的更新按钮
			$("#emp_update_btn").attr("edit-id", $(this).attr("edit-id"));
			$("#empUpdateModal").modal({
				backdrop : 'static'
			});
		});
		
		function getEmp(id) {
			$.ajax({
				url : "${APP_PATH}/emp/" + id,
				type : "GET",
				success : function(result) {
					console.log(result);
					var empData = result.extend.emp;
 					$("#empName_update_static").text(empData.empName);
					$("#email_update_input").val(empData.email);
					$("#empUpdateModal input[name=gender]").val(
							[ empData.gender ]);
					$("#empUpdateModal select").val([ empData.dId ]) ;
				}
			});
		}
		
		//点击更新，更新员工信息
		$("#emp_update_btn").click(function() {
			//验证邮箱是否合法
			//1.校验邮箱
			var email = $("#email_update_input").val();
			var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if (!regEmail.test(email)) {
				show_validate_msg("#email_update_input", "error", "邮箱格式不正确");
				return false;
			} else {
				show_validate_msg("#email_update_input", "success", "");
			}
			//2.发送ajax请求，保存更新的信息
			$.ajax({
				url : "${APP_PATH}/emp/" + $(this).attr("edit-id"),
				//type:"POST",
				//data:$("#empUpdateModal form").serialize()+"&_method=PUT",
				//使用put请求需要在web xml配置过滤器
				type : "PUT",
				data : $("#empUpdateModal form").serialize(),
				success : function(result) {
					alert(result.msg);
					//1.关闭对话框
					$("#empUpdateModal").modal("hide");
					//2.回到本页面
					to_page(currentPage); 
				}
			});
		});
		
		//单个删除
		$(document).on("click", ".delete_btn", function() {
			//弹出是否确认删除员工
			var empName = $(this).parents("tr").find("td:eq(2)").text();
			//alert($(this).parents("tr").find("td:eq(1)").text());
			if (confirm("确认删除 [" + empName + "] 吗?")) {
				//确认，发送ajax请求删除即可
				$.ajax({
					url : "${APP_PATH}/emp/" + $(this).attr("del-id"),
					type : "DELETE",
					success : function(result) {
						alert(result.msg);
						//2.回到本页面
						to_page(currentPage);
					}
				});
			}
		});
		
		//完成全选/全不选按钮功能
		$("#check_all").click(function() {
			//attr获取checked是underfied;
			//我们这些dom原生的属性，用prop;attr用来获取自定义的属性
			//prop修改和读取dom原生的属性值
			//alert($(this).prop("checked"));
			$(".check_item").prop("checked", $(this).prop("checked"));
		});
		
		//check_item
		$(document).on("click",".check_item",function() {
			//判断当前选择中的元素是否是5个
			var flag = $(".check_item:checked").length == $(".check_item").length;
			$("#check_all").prop("checked", flag);
		});
		
		
		//点击全部删除，就批量删除
		$("#emp_delete_all_btn").click(function() {
			var empNames = "";
			var del_idstr = "";
			$.each($(".check_item:checked"), function() {
				empNames += $(this).parents("tr").find("td:eq(2)").text()+",";
				del_idstr+=$(this).parents("tr").find("td:eq(1)").text()+"-";
			});
			//去除empsNames多于的  ，
			empNames = empNames.substring(0,empNames.length-1);
			//去除多于的  -
			del_idstr = del_idstr.substring(0,del_idstr.length-1);
			if(confirm("确认删除["+empNames+"]吗?")){
				//发送ajax请求删除
				$.ajax({
					url:"${APP_PATH}/emp/"+del_idstr,
					type:"DELETE",
					success:function(result){
						alert(result.msg);
						to_page(currentPage);
					}
				});
			}
		});
	</script>
</body>
</html>