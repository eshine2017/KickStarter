//example
//
//<canvas id="myChart1" width="400" height="400"></canvas>
//<canvas id="myChart2" width="400" height="400"></canvas>
//
//var dataSource = {
//    labels: ["Africa", "Asia", "Europe", "Latin America", "North America", "Other"],
//    data: [2478,5267,734,784,433,222]
//};
//
//pieChart("myChart1", dataSource);
//barChart("myChart2", dataSource);

// input: canvasId(where to generate the graph), dataSource({labels:[],data:[]})
function barChart(canvasId, dataSource) {
    
    // colors templates
    var colorSets = ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850","#BE6E46","#CDE7B0","#A3BFA8","#7286A0","#59594A"];
    var data = {
    	labels: dataSource.labels,
        datasets: [{
            data: dataSource.data,
            backgroundColor: colorSets
        }]
    };

    var ctx = document.getElementById(canvasId).getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: data,
        options: {}
    });
}

// input: canvasId(where to generate the graph), dataSource({labels:[],data:[]})
function pieChart(canvasId, dataSource) {

    // colors templates
    var colorSets = ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850","#BE6E46","#CDE7B0","#A3BFA8","#7286A0","#59594A"];
    var data = {
        labels: dataSource.labels,
        datasets: [{
            data: dataSource.data,
            backgroundColor: colorSets
        }]
    };

    var ctx = document.getElementById(canvasId).getContext('2d');
    var myPieChart = new Chart(ctx, {
        type: 'pie',
        data: data,
        options: {}
    });
}
