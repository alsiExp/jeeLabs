<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>RESTful web service</title>

    <!-- CSS -->
    <link href="bootstrap.css" rel="stylesheet" type="text/css">

</head>

<body>
<div class="container">

    <section class="row" id="main-content">

        <h2>Web service js client</h2>

        <div class="row">
            <div class="col-md-6">
                    <div class="form-group">
                        <label for="search_name">Введите имя для поиска</label>
                        <input type="text" class="form-control" id="search_name" placeholder="Имя">
                    </div>
                    <button id="search_ok" type="button" class="btn btn-default">Искать</button>

            </div>
            <div class="col-md-6">

            </div>

        </div>
    </section>
</div>
<!-- javascript -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">
    $("#search_ok").click(function () {
        var name = $("#search_name").val();
        $.post("/api/human/search",
                {
                    name: name
                }
        ).done(function (data ) {
            console.log(data);
        })

    });

    /*
     for js client:
     $.post(
     "/api/human/search",
     {
     param1: "param1",
     param2: 2
     }
     );
     */
</script>
</body>
</html>
