var dato = []; // y
var datox = []; // x
var datoy4 = []; // y
var datox4 = []; // x
var test = [];
var grafico1x = [];
var grafico1y = [];
var subtext1 = '';
var subtext2 = '';
var metaDiaria;
var producto = [];
var mesesProducto = [];
var productosname = [];
var cantidadproductos = [];
$.ajax({
    url: 'http://localhost:7070/api/cantidadProductosVendidos',
    type: 'GET',
    data: {},
    dataType: 'json',
    success: function (res) {
        for (var i = 0; i < res.length; i++) {
            productosname.push(res[i].nombre);
            cantidadproductos.push(res[i].stock);
        }
        console.log(res);
    }

});
$.ajax({
    url: 'http://localhost:7070/api/g_metaDiaria',
    type: 'GET',
    data: {},
    dataType: 'json',
    success: function (res) {
        metaDiaria = res / 1000;
    }

});
$.ajax({
    url: 'http://localhost:7070/api/productosIngresados',
    type: 'GET',
    data: {},
    dataType: 'json',
    success: function (res) {
        for (var i = 0; i < res.length; i++) {
            let fecha = new Date();
            let meses = ["enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"];
            let mes = meses[fecha.getMonth() - i];
            producto.push(res[i]);
            mesesProducto.push(mes);
        }
        console.log(producto);
        console.log(mesesProducto);
    }

});

$.ajax({
    url: 'http://localhost:7070/api/obtenerResumenVentasMensuales',
    type: 'GET',
    data: {},
    dataType: 'json',
    success: function (res) {
        var total = 0;
        for (var i = 0; i < res.length; i++) {
            dato.push(res[i].ventas); // Recupere las categorías una por una y complete la matriz de categorías
            datox.push(res[i].mes);
            total += res[i].ventas;
        }
        subtext2 = 'Total: ' + total;
    }

});
$.ajax({
    url: 'http://localhost:7070/api/obtenerProductosMasVendidos',
    type: 'GET',
    data: {},
    dataType: 'json',
    success: function (res) {
        for (var i = 0; i < res.length; i++) {
            test.push(res[i]);
        }
    }

});
$.ajax({
    url: 'http://localhost:7070/api/obtenerResumenVentasUltimosMeses',
    type: 'GET',
    data: {},
    dataType: 'json',
    success: function (res) {
        var total = 0;
        for (var i = 0; i < res.length; i++) {
            grafico1x.push(res[i].ventas); // Recupere las categorías una por una y complete la matriz de categorías
            grafico1y.push(res[i].meses);
            total = total + res[i].ventas;
        }
        subtext1 = 'Total: ' + total;
    }

});

const getOptionChart1 = () => {
    return {
        title: {
            //text: 'Los ultimos 5 dias de ventas',
            subtext: subtext1
        },
        tooltip: {
            show: true,
            trigger: "axis",
            triggerOn: "mousemove|click"
        },
        dataZoom: {
            show: true,
            start: 0
        },
        xAxis: [
            {
                type: "category",
                data: grafico1y
            }
        ],
        yAxis: [
            {
                type: "value"
            }
        ],
        series: [
            {
                data: grafico1x,
                type: "line"
            }
        ]
    };
};
const getOptionChart6 = () => {
    return {
        xAxis: {
            type: 'category',
            axisLabel: {interval: 0, rotate: 70},
            data: productosname
        },
        yAxis: {
            type: 'value'
        },
        tooltip: {
            show: true,
            trigger: "axis",
            triggerOn: "mousemove|click"
        },
        series: [
            {
                data: cantidadproductos,
                type: 'bar',
                smooth: true
            }
        ]

    };

};
const getOptionChart5 = () => {
    return {
        title: {
            //text: 'Ventas por mes',
            subtext: subtext2
        },
        tooltip: {
            show: true
        },
        xAxis: {
            type: "category",
            data: mesesProducto
        },
        yAxis: {
            type: "value"
        },
        series: [
            {
                data: producto,
                type: "bar"
            }
        ]
    };

};
const getOptionChart2 = () => {
    return {
        title: {
            //text: 'Ventas por mes',
            subtext: subtext2
        },
        tooltip: {
            show: true
        },
        xAxis: {
            type: "category",
            data: datox
        },
        yAxis: {
            type: "value"
        },
        series: [
            {
                data: dato,
                type: "bar"
            }
        ]
    };

};

const getOptionChart3 = () => {
    return {

        tooltip: {
            trigger: "item"
        },
        legend: {
            top: "0%",
            left: "center"
        },
        series: [
            {
                top: "10%",
                name: "Producto",
                type: "pie",
                radius: ["40%", "70%"],
                avoidLabelOverlap: false,
                itemStyle: {
                    borderRadius: 10,
                    borderColor: "#fff",
                    borderWidth: 2
                },
                label: {
                    show: false,
                    position: "center"
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: "20",
                        fontWeight: "bold"
                    }
                },
                labelLine: {
                    show: false
                },
                data: test
            }
        ]
    };
};
const getOptionChart4 = () => {
    return {

        series: [
            {
                type: 'gauge',
                startAngle: 180,
                endAngle: 0,
                center: ['50%', '75%'],
                radius: '90%',
                min: 0,
                max: 1,
                splitNumber: 8,
                axisLine: {
                    lineStyle: {
                        width: 6,
                        color: [
                            [0.25, '#FF6E76'],
                            [0.5, '#FDDD60'],
                            [0.75, '#58D9F9'],
                            [1, '#7CFFB2']
                        ]
                    }
                },
                pointer: {
                    icon: 'path://M12.8,0.7l12,40.1H0.7L12.8,0.7z',
                    length: '12%',
                    width: 20,
                    offsetCenter: [0, '-60%'],
                    itemStyle: {
                        color: 'inherit'
                    }
                },
                axisTick: {
                    length: 12,
                    lineStyle: {
                        color: 'inherit',
                        width: 2
                    }
                },
                splitLine: {
                    length: 20,
                    lineStyle: {
                        color: 'inherit',
                        width: 5
                    }
                },
                axisLabel: {
                    color: '#464646',
                    fontSize: 20,
                    distance: -60,
                    rotate: 'tangential',
                    formatter: function (value) {
                        if (value === 0.875) {
                            return '';
                        } else if (value === 0.625) {
                            return '';
                        } else if (value === 0.375) {
                            return '';
                        } else if (value === 0.125) {
                            return '';
                        }
                        return '';
                    }
                },
                title: {
                    offsetCenter: [0, '-10%'],
                    fontSize: 20
                },
                detail: {
                    fontSize: 30,
                    offsetCenter: [0, '-35%'],
                    valueAnimation: true,
                    formatter: function (value) {
                        return 'S/.' + Math.round(value * 1000) + '';
                    },
                    color: 'inherit'
                },
                data: [
                    {
                        value: metaDiaria,
                        name: 'Meta (S/.1000)'
                    }
                ]
            }
        ]



    };



};



const initCharts = () => {
    const chart1 = echarts.init(document.getElementById("chart1"));
    const chart2 = echarts.init(document.getElementById("chart2"));
    const chart3 = echarts.init(document.getElementById("chart3"));
    const chart4 = echarts.init(document.getElementById("chart4"));
    const chart5 = echarts.init(document.getElementById("chart5"));
    const chart6 = echarts.init(document.getElementById("chart6"));
    chart1.setOption(getOptionChart1());
    chart2.setOption(getOptionChart2());
    chart3.setOption(getOptionChart3());
    chart4.setOption(getOptionChart4());
    chart5.setOption(getOptionChart5());
    chart6.setOption(getOptionChart6());
    chart1.resize();
    chart2.resize();
    chart3.resize();
    chart4.resize();
    chart5.resize();
    chart6.resize();
};

window.addEventListener("load", () => {
    initCharts();
});







//});
//function echart(dato) {
//    // Inicializa la instancia echarts basada en el dom preparado
//    var chart3 = echarts.init(document.getElementById("chart3"));
//
//    // Especifica los elementos de configuración y los datos del gráfico
//    var option = {
//        tooltip: {
//            show: true
//        },
//        xAxis: {
//            data: ["11", "12"]
//        },
//        yAxis: {
//            type: "value"
//        },
//        series: [
//            {
//                data: dato,
//                type: "bar"
//            }
//        ]
//    };
//
//    // Use el elemento de configuración y los datos que se acaban de especificar para mostrar el gráfico.
//    chart3.setOption(option);
//    chart3.resize();
//}
//
//
//$.ajax({
//    type: "post",
//    async: true, // Solicitud asincrónica (la solicitud sincrónica bloqueará el navegador, otras operaciones del usuario deben esperar a que se complete la solicitud antes de que se pueda ejecutar)
//    url: "TestServlet", // Solicitud enviada a TestServlet
//    data: {},
//    dataType: "json", // El formato de datos devuelto es json
//    success: function (result) {
//        // El contenido de la función se ejecuta cuando la solicitud es exitosa, y el resultado es el objeto json devuelto por el servidor
//        if (result) {
//
//            for (var i = 0; i < result.length; i++) {
//                names.push(resultado [i].name); // Recupere las categorías una por una y complete la matriz de categorías
//            }
//
//            for (var i = 0; i < result.length; i++) {
//                nums.push(resultado [i].num); // Obtenga el volumen de ventas uno por uno y complete la matriz de volumen de ventas
//            }
//
//            // Renderiza la imagen
//            echart(names, nums)
//
//        }
//
//    },
//    error: function (errorMsg) {
//        // Ejecuta esta función cuando la solicitud falla
//        alerta("Fallo en los datos de solicitud de gráfico");
//        myChart.hideLoading();
//    }
//})
//
//
//function echart(names, nums) {
//    // Inicializa la instancia echarts basada en el dom preparado
//    var myChart = echarts.init(document.getElementById('main'));
//
//    // Especifica los elementos de configuración y los datos del gráfico
//    var option = {
//        title: {
//            texto: 'Ejemplo de introducción de ECharts'
//        },
//        tooltip: {},
//        legend: {
//            datos: ['Ventas']
//        },
//        xAxis: {
//            data: names
//        },
//        yAxis: {},
//        series: [{
//                nombre: 'Ventas',
//                type: 'bar',
//                data: nums
//            }]
//    };
//
//    // Use el elemento de configuración y los datos que se acaban de especificar para mostrar el gráfico.
//    myChart.setOption(option);
//}
//
//
//window.addEventListener("load", () => {
//    initCharts();
