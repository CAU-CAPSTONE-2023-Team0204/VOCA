import { Chart, registerables } from "chart.js";
import ChartDataLabels from "chartjs-plugin-datalabels";

export const centerTextPlugin2 = {
  id: "text",
  afterDraw: function (chart, a, b) {
    let width = chart.width,
      height = chart.height,
      ctx = chart.ctx;

    ctx.restore();
    let fontSize = (height / 200).toFixed(2);
    ctx.font = fontSize + "em sans-serif";
    ctx.textBaseline = "middle";

    let text = "90%",
      textX = Math.round((width - ctx.measureText(text).width) / 2),
      textY = height / 2;

    ctx.fillText(text, textX, textY);
    ctx.save();
  },
};

const getGradient = (ctx, chartArea, start_color, stop_color, max, current) => {
  let width, height, gradient;
  const chartWidth = chartArea.right - chartArea.left;
  const chartHeight = chartArea.bottom - chartArea.top - 100;
  if (gradient === null || width !== chartWidth || height !== chartHeight) {
    // Create the gradient because this is either the first render
    // or the size of the chart has changed
    width = chartWidth;
    height = chartHeight;
    gradient = ctx.createLinearGradient(
      0,
      chartArea.bottom - (current / max) * chartHeight - 100,
      0,
      chartArea.bottom
    );
    gradient.addColorStop(0, start_color);
    gradient.addColorStop(1, "white");
  }
  return gradient;
};

export const barConfig = (data, labels) => {
  console.log(data);
  return {
    type: "bar",
    data: {
      labels: ["40", "50", "60", "70", "80", "90", "100"],
      datasets: [
        {
          data: data,
          backgroundColor: [
            "#5DC5C6",
            "#3AB54A",
            "#8FC63E",
            "#5DC5C6",
            "#3AB54A",
            "#8FC63E",
            "#5DC5C6",
          ],
          gradientStart: [
            "#5DC5C6",
            "#3AB54A",
            "#8FC63E",
            "#5DC5C6",
            "#3AB54A",
            "#8FC63E",
            "#5DC5C6",
          ],
          borderRadius: 40,
        },
      ],
    },
    plugins: [
      {
        id: "chartAreaColor",
        beforeDraw: (chart) => {
          const {
            ctx,
            chartArea: { top, left, width, height },
          } = chart;
          ctx.fillStyle = "#FFFFFF";
          ctx.fillRect(left, top, width, height);
        },
      },
      {
        id: "gradientColor",
        beforeDatasetDraw: (chart) => {
          const { ctx, chartArea } = chart;
          const gradientStart =
            chart.config._config.data.datasets[0].gradientStart;
          const data = chart.config._config.data.datasets[0].data;
          const maximumValue = Math.max(...data);

          const length = gradientStart.length;
          for (let i = 0; i < length; i++) {
            chart.config._config.data.datasets[0].backgroundColor[i] =
              getGradient(
                ctx,
                chartArea,
                gradientStart[i],
                "white",
                maximumValue,
                data[i]
              );
          }
        },
      },
      ChartDataLabels,
    ],
    options: {
      responsive: true,
      maintainAspectRatio: true,
      events: [],
      scales: {
        y: {
          grid: {
            display: true,
          },
          beginAtZero: true,
        },
        x: {
          grid: {
            display: false,
          },
        },
      },
      plugins: {
        customCanvasBackgroundColor: {
          color: "#FFFFFF",
        },
        legend: { display: false },
        datalabels: {
          anchor: "end",
          align: "top",
          offset: -50,
          formatter: Math.round,
          color: "white",
          font: {
            family: "Ones",
            weight: "bold",
            size: 20,
          },
        },
      },
    },
  };
};

export const doughnutConfigWithCenter = (percentage, color, label) => {
  return {
    type: "pie",
    data: {
      datasets: [
        {
          label: "outer",
          data: [percentage, 100 - percentage],
          backgroundColor: [color, "#eef6e3"],
          rotation: 0,
          cutout: "50%",
          borderWidth: 0,
        },
        {
          // dummy dataset to fill middle.
          label: "inner",
          data: [1],
          backgroundColor: "#FFFFFF",
          weight: 3,
          borderWidth: 0,
        },
      ],
    },
    options: {
      borderAlign: "inner",
      responsive: false,
      events: [],
      shadow: {
        id: "pie",
      },
    },
    plugins: [
      {
        id: "text",
        afterDraw: function (chart, a, b) {
          let width = chart.width,
            height = chart.height,
            ctx = chart.ctx;

          ctx.restore();
          let fontSize = (height / 150).toFixed(2);
          ctx.font = fontSize + "em Ones";
          ctx.fillStyle = color;
          ctx.textBaseline = "middle";
          let text = Math.round(percentage) + "%",
            textX = Math.round((width - ctx.measureText(text).width) / 2),
            textY = height / 2 + fontSize * 10;
          let text2 = label,
            textX2 = Math.round((width - ctx.measureText(text2).width) / 2),
            textY2 = height / 2 - fontSize * 10;

          ctx.fillText(text, textX, textY);
          ctx.fillText(text2, textX2, textY2);
          ctx.save();
        },
      },
      {
        id: "shadow-plugin",
        beforeDraw: function (chart) {
          if (chart.config._config.options?.shadow?.id === "pie") {
            var ctx = chart.ctx;
            const centerCircle = chart.data.datasets[1];

            const renderShadow = (set) => {
              const _fill = ctx.fill;
              ctx.fill = function () {
                ctx.save();
                ctx.shadowColor = "rgba(2, 35, 68, 0.05)";
                ctx.shadowBlur = 1;
                ctx.shadowOffsetY = 3;
                _fill.apply(this, arguments);
                ctx.restore();
              };
            };
            renderShadow(centerCircle);
          }
        },
      },
    ],
  };
};
