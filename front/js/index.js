var ctx = document.getElementById('my_canvas1').getContext('2d');
var ctx1 = document.getElementById('my_canvas2').getContext('2d');
var al = 0;
var start = 4.72;
var cw = ctx.canvas.width;
var ch = ctx.canvas.height; 
var diff;
function progressSim(){
	diff = ((al / 100) * Math.PI*2*10).toFixed(2);
	ctx.clearRect(0, 0, cw, ch);
	ctx.lineWidth = 5;
	ctx.fillStyle = '#000';
  ctx.font = '18px Dosis';
	ctx.strokeStyle = "rgb(68,138,255)";
	ctx.textAlign = 'center';
	ctx.fillText(al+'%', cw*.5, ch*.5+5, cw);
	ctx.beginPath();
	ctx.arc(72, 72, 60, start, diff/10+start, false);
	ctx.stroke();
	if(al >= 75){
		clearTimeout(sim);
	    // Add scripting here that will run when progress completes
	}
	al++;
}
var sim = setInterval(progressSim, 30);


//second one

var al1 = 0;
var start1 = 4.72;
var cw1 = ctx.canvas.width;
var ch1 = ctx.canvas.height; 
var diff1;
function progressSim1(){
	diff1 = ((al1 / 100) * Math.PI*2*10).toFixed(2);
	ctx1.clearRect(0, 0, cw, ch);
	ctx1.lineWidth = 5;
	ctx1.fillStyle = '#000';
  ctx1.font = '18px Dosis';
	ctx1.strokeStyle = "rgb(68,138,255)";
	ctx1.textAlign = 'center';
	ctx1.fillText(al1+'%', cw1*.5, ch1*.5+5, cw1);
	ctx1.beginPath();
	ctx1.arc(72, 72, 60, start1, diff1/10+start1, false);
	ctx1.stroke();
	if(al1 >= 50){
		clearTimeout(sim1);
	    // Add scripting here that will run when progress completes
	}
	al1++;
}
var sim1 = setInterval(progressSim1, 50);

//horizontal percentage bar

var hBar = document.getElementById("HourlyBar");
    var width = 1;
    var id = setInterval(frame, 30);
    function frame() {
        if (width >= 70) {
            clearInterval(id);
        } else {
            width++;
            hBar.style.width = width + '%';
        }
    }

var mBar = document.getElementById("MonthlyBar");
    var widthM = 1;
    var idM = setInterval(frameM, 80);
    function frameM() {
        if (widthM >= 20) {
            clearInterval(idM);
        } else {
            widthM++;
            mBar.style.width = widthM + '%';
        }
    }

var yBar = document.getElementById("YearlyBar");
    var widthY = 1;
    var idY = setInterval(frameY, 30);
    function frameY() {
        if (widthY >= 70) {
            clearInterval(idY);
        } else {
            widthY++;
            yBar.style.width = widthY + '%';
        }
    }

//chartjs Barchart

// var dataLChart = {
//   labels: ["January", "February", "March", "April", "May", "June", "July"],
//   datasets: [{
//     label: "My First dataset",
//     fillColor: "rgba(220,220,220,0.2)",
//     strokeColor: "rgba(220,220,220,1)",
//     pointColor: "rgba(220,220,220,1)",
//     pointStrokeColor: "#fff",
//     pointHighlightFill: "#fff",
//     pointHighlightStroke: "rgba(220,220,220,1)",
//     data: [65, 59, 80, 81, 56, 55, 40]
//   }, {
//     label: "My Second dataset",
//     fillColor: "rgba(151,187,205,0.2)",
//     strokeColor: "rgba(151,187,205,1)",
//     pointColor: "rgba(151,187,205,1)",
//     pointStrokeColor: "#fff",
//     pointHighlightFill: "#fff",
//     pointHighlightStroke: "rgba(151,187,205,1)",
//     data: [28, 48, 40, 19, 86, 27, 90]
//   }]
// };

// var LOptions = {
//   bezierCurve: false,
//   animation: true,
//   animationEasing: "easeOutQuart",
//   showScale: false,
//   tooltipEvents: ["mousemove", "touchstart", "touchmove"],
//   tooltipCornerRadius: 3,
//   pointDot : true,
//   pointDotRadius : 4,
//   datasetFill : true,
//   scaleShowLine : true,
//   animationEasing : "easeOutBounce",
//   animateRotate : true,
//   animateScale : true,
// };


// var contextOfChart = document.getElementById("myChart").getContext("2d");

// var mychart = new Chart(contextOfChart).Line(dataLChart, LOptions);


var ctxOfBarChart = document.getElementById("myChart").getContext("2d");

var mychart = new Chart(ctxOfBarChart, {
  type: 'bar',
  data: {
    labels: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"],
    datasets: [
      {
        label: "Campaing2",
        backgroundColor: [
          'rgba(68,138,255,1)', 'rgba(68,138,255,1)', 'rgba(68,138,255,1)', 'rgba(68,138,255,1)', 'rgba(68,138,255,1)', 'rgba(68,138,255,1)', 'rgba(68,138,255,1)', 'rgba(68,138,255,1)', 'rgba(68,138,255,1)', 'rgba(68,138,255,1)', 'rgba(68,138,255,1)', 'rgba(68,138,255,1)', 'rgba(68,138,255,1)'
        ],
        borderWidth: 2,
        data: [10, 20, 15, 25, 15, 13, 16, 23, 45, 12, 44, 29]
      },
      {
        label: "Campaing3",
        backgroundColor: [
          'rgba(68,138,255,0.3)', 'rgba(68,138,255,0.3)', 'rgba(68,138,255,0.3)', 'rgba(68,138,255,0.3)', 'rgba(68,138,255,0.3)', 'rgba(68,138,255,0.3)', 'rgba(68,138,255,0.3)', 'rgba(68,138,255,0.3)', 'rgba(68,138,255,0.3)', 'rgba(68,138,255,0.3)', 'rgba(68,138,255,0.3)', 'rgba(68,138,255,0.3)', 'rgba(68,138,255,0.3)',
        ],
        borderWidth: 2,
        data: [20, 10, 25, 15, 35, 3, 36, 23, 35, 32, 24, 19]
      }
    ]
  },
  options: {
    scales: {
      xAxes: [
        {
          barThickness: 18
        }
      ],
      yAxes: [{
        barThickness: 5,
        ticks: {
          beginAtZero: true
        }
      }]
    }
  }
});

//counter1
var cuntr1 = document.getElementsByClassName("counter1");

setInterval(function () {
    for(var i = 0 ; i < cuntr1.length ; i++) {
        
        cuntr1[i].innerHTML = (parseFloat(parseFloat(counters[i].innerHTML).toFixed(2)) + parseFloat(parseFloat(counters[i].dataset.increment).toFixed(2))).toFixed(2);
    }
}, 1000);