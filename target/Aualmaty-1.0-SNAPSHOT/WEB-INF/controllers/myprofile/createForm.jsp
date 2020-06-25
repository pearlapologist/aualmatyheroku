<%-- 
    Document   : createForm
    Created on : 18.06.2020, 11:36:14
    Author     : bayan
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="models.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Person p = models.Account.getCurrentPerson(request);
    DbHelper db = new DbHelper();
    int personId = p.getId();
    Executor executor = null;
    int executorId = db.getExecutorIdByPersonId(personId);
    if (executorId != -1) {
        executor = db.getExecutor(executorId);
        db.loadExecutorServices(executor);
    }
    request.setAttribute("navCurr", "cabinet");

    ArrayList<Service> services = new ArrayList();
    ArrayList<Service> selectionList;
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Мои кабинет</title>

        <script>
            var count = 0;

            var services = [];

            function clickCreateService(button) {
                var title = $("#newServiceTitle").val();
                var price = $("#newServicePrice").val();

                $("<p class='col-5' style='margin: 15px;' id ='servTiltle" + count + "'>" + title + "</p>").appendTo("#createformservices");
                $("<p class='col-5' style='margin: 15px;' id ='servPrice" + count + "'>" + price + "</p>").appendTo("#createformservices2");
                $("<input  type='checkbox' style='margin: 15px;' id='servCheck" + count + "' value='select" + count + "'></input>").appendTo("#createformservices3");
                count++;
            }

            function fdeleteService(button) {
                $('#deleteServiceModal').modal("hide");

                $('input:checkbox:checked').each(function () {
                    var checkbox = $("input:checkbox[value='" + ($(this).val()) + "']");
                    var index = checkbox.index();
                    var in2 = index--;
                    $("#createformservices2 p:eq(" + in2 + ")").remove();

                    $("#createformservices p:eq(" + in2 + ")").remove();
                    checkbox.remove();

                });
            }

            array services = [];
            var item = {id: 1, title: 'title', price: 2000};
            services.push(item);
            console.log(services);
            function fsaveForm(button) {
                localStorage.setItem("createFormServices", JSON.stringify(services));
                var vstring = JSON.stringify(services);
            }

        </script>
        <%
           /* int l = lokm;
            for(int i=0;i<10;i++){
            String s =  $("#createformservices p:eq(" + 2 + ")").remove();}*/
           // request.setAttribute("createFormServices", services);
        %>

    </head>
    <body>
        <jsp:include page="/WEB-INF/auth/header.jsp"/>
        <div class="container-fluid">
            <div class="card" ">
                <div class="row no-gutters">
                    <div class="col-sm-1" style="background: #868e96;">
                        <img src="<%=models.DataUtils.getPersonPhotoPath(p)%>" class="card-img-top " alt="...">
                    </div>
                    <p  style=" padding: 20px;"><b><%=p.getName() + " " + p.getLastname()%></b></p> 

                </div>
            </div>

            <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
                </button>
                <div class="collapse navbar-collapse " id="collapsibleNavbar">
                    <nav class="nav nav-pills nav-justified">
                        <a class="nav-link " href="<%=DataUtils.PATH%>MyCabinet">Профиль</a>
                        <%
                            boolean isexecutor = db.getPersonIsExecutorField(p.getId());
                        %> 

                        <a class=" nav-link active" href="<%=DataUtils.PATH%><%if (isexecutor == true) {
                                out.print("MyForm");
                            }
                            else {
                                out.print("CreateForm");
                            }
                           %> ">Анкета</a>
                        <a class=" nav-link" href="<%=DataUtils.PATH%>MyOrders">Мои заказы</a>
                        <a class=" nav-link" href="<%=DataUtils.PATH%>MyReviews">Мои отзывы</a>
                    </nav>
                </div>
            </nav>
            <div>
                <form id='createformId'  enctype="multipart/form-data" action="CreateForm" method="post">
                    <div class="row no-gutters">
                        <div class="col-md-4 mb-3">
                            <label for="myformcoverimage">Фотография обложки:</label>
                            <input id="myformcoverimage" type="file" class="form-control" name="myformcoverimage" multiple="multiple" />
                        </div>
                        <div class="col-sm-3">
                            <button type="button" class="btn btn-dark" data-toggle="modal" data-target="#newServiceModal">
                                Добавить фотографию
                            </button>
                        </div>
                    </div>


                    <div class="col-md-4 mb-3">
                        <label for="myformspec">Специализация:</label>
                        <input id="myformspec" type="text" class="form-control" name="myformspec" multiple="multiple" />
                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="myformdesc">Описание вашей деятельности:</label>
                        <input id="myformdesc" type="text" class="form-control" name="myformdesc" multiple="multiple" />
                    </div>


                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Раздел
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <%
                                ArrayList<String> sections = db.getSectionsInString();
                                for (String s : sections) {%>
                            <a class="dropdown-item"><%=s%></a>
                            <%}

                            %>

                        </div>
                    </div>

                    <div class="row no-gutters">
                        <div class="col-sm-4">Услуги
                        </div>
                        <div class="col-sm-4">
                            <button type="button" class="btn btn-dark" data-toggle="modal" data-target="#newServiceModal">
                                Добавить услугу
                            </button>
                        </div>
                        <div class="col-sm-3">
                            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteServiceModal">
                                Удалить
                            </button>
                        </div>
                    </div>

                    <div class="row no-gutters col-11">
                        <div class='card-body col-5' id='createformservices'></div>    
                        <div class='card-body col-5' id="createformservices2"></div>
                        <div class='card-body col-1' id='createformservices3'></div>
                    </div>   
                </form> 
                <button id="submitFiles" class="btn btn-primary" type="submit" onclick="fsaveForm(this)" name="button">Создать</button>
            </div>
        </div>
    </body>

    <div class="modal fade" id="newServiceModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form method="post" action="AddService">
                    <div class="modal-header">
                        <h5 class="modal-title" id="responseModalLabel">Добавить услугу</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <label for="newServiceTitle">Название</label>
                        <input type="text" class="form-control is-valid" id="newServiceTitle" name="newServiceTitle" value="" required>
                        <div class="invalid-feedback">
                            Пожалуйста, введите валидные данные.
                        </div>


                        <label for="newServicePrice">Цена</label>
                        <input type="number" class="form-control is-valid" id="newServicePrice" name="newServicePrice" value="" required>
                        <div class="invalid-feedback">
                            Пожалуйста, введите валидные данные.
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                        <button type="submit" id="btnsaveaddservice" class="btn btn-secondary" onclick="clickCreateService(this)" data-dismiss="modal">Сохранить</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <div id="deleteServiceModal" class="modal fade" tabindex="-1">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">  
                    <h4 class="modal-title">Предупреждение</h4>
                    <button class="close" data-dismiss='modal'>x</button>
                </div>
                <div class="modal-body">
                    Вы уверены, что хотите удалить выбранный элемент?
                </div>
                <div class="modal-footer">
                    <input  class="btn btn-info" type="submit" name="btnDeleteService" id="btnDeleteService" onclick="fdeleteService(this)" value="Да">
                </div>
            </div>
        </div>
    </div>

</html>
