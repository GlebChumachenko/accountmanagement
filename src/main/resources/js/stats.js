google.charts.load("current", {packages: ["corechart"]});
google.charts.setOnLoadCallback(draw);
google.charts.setOnLoadCallback(draw1);
google.charts.setOnLoadCallback(draw2);
google.charts.setOnLoadCallback(draw3);
google.charts.setOnLoadCallback(draw4);

function draw() {
    let res = [['Год', 'Продажа', 'Закупка', 'Зарплата', 'Маркетинг', 'Другое']];

    for (let i = 0; i < dates.length; i++) {
        res.push([dates[i], floatAccountSell[i],floatAccountBuy[i], floatCostSalary[i], floatCostMarketing[i], floatCostOther[i]]);
    }

    var data = google.visualization.arrayToDataTable(res);

    var options = {
        title: '', hAxis: {title: 'Дата', titleTextStyle: {color: '#333'}}, vAxis: {minValue: 0}
    };

    var chart = new google.visualization.AreaChart(document.getElementById('draw'));
    chart.draw(data, options);

}

function draw1() {
    let res = [['Категория', 'Сумма']];

    for (let i = 0; i < accountString.length; i++) {
        res.push([accountString[i], accountFloat[i]]);
    }

    var data = google.visualization.arrayToDataTable(res);

    let options = {
        title: '',
        hAxis: {title: 'Категория'},
        vAxis: {title: 'Сумма'},
        bar: {groupWidth: "80%"},
        legend: {position: "none"}
    };

    let chart = new google.visualization.ColumnChart(document.getElementById('draw1'));
    chart.draw(data, options);
}

function draw2() {
    let res = [['Категория', 'Сумма']];

    for (let i = 0; i < accountString.length; i++) {
        res.push([accountString[i], accountFloat[i]]);
    }

    var data = google.visualization.arrayToDataTable(res);

    var options = {
        title: '', is3D: true,
    };

    var chart = new google.visualization.PieChart(document.getElementById('draw2'));
    chart.draw(data, options);

}

function draw3() {
    let res = [['Категория', 'Сумма']];

    for (let i = 0; i < costString.length; i++) {
        res.push([costString[i], costFloat[i]]);
    }

    var data = google.visualization.arrayToDataTable(res);

    let options = {
        title: '',
        hAxis: {title: 'Категория'},
        vAxis: {title: 'Сумма'},
        bar: {groupWidth: "80%"},
        legend: {position: "none"}
    };

    let chart = new google.visualization.ColumnChart(document.getElementById('draw3'));
    chart.draw(data, options);
}

function draw4() {
    let res = [['Категория', 'Сумма']];

    for (let i = 0; i < costString.length; i++) {
        res.push([costString[i], costFloat[i]]);
    }

    var data = google.visualization.arrayToDataTable(res);

    var options = {
        title: '', is3D: true,
    };

    var chart = new google.visualization.PieChart(document.getElementById('draw4'));
    chart.draw(data, options);

}

