import useAxiosPrivate from "../hooks/useAxiosPrivate";
import TeacherSidebar from "./TeacherSidebar";
import NavigationBar from "./NavigationBar";
import React from "react";
import "../styles/class_main.css";
import { Chart, registerables } from "chart.js";
import { barConfig, doughnutConfigWithCenter } from "../api/chart";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { axiosPrivate } from "../api/axios";

const ClassMain = () => {
  const { class_id } = useParams();
  const [latestData, setLatestData] = useState({});

  Chart.register(...registerables);
  var charts = [];
  useEffect(() => {
    const canvases = [
      document.getElementById("up_canvas"),
      document.getElementById("down_canvas_1"),
      document.getElementById("down_canvas_2"),
      document.getElementById("down_canvas_3"),
    ];

    axiosPrivate.get("/api/test/result/latest/me").then((response) => {
      new Promise((resolve, reject) => {
        setLatestData(response.data);
        charts.forEach((chart) => {
          chart.destroy();
        });
        resolve(response.data);
      }).then((latestData) => {
        const barConfig1 = barConfig([
          latestData.scoreDistribution.belowForty,
          latestData.scoreDistribution.fifties,
          latestData.scoreDistribution.sixties,
          latestData.scoreDistribution.seventies,
          latestData.scoreDistribution.eighties,
          latestData.scoreDistribution.nineties,
          latestData.scoreDistribution.perfect,
        ]);
        charts[0] = new Chart(canvases[0], barConfig1);

        const pieConfig1 = doughnutConfigWithCenter(
          latestData.average,
          "#8FC63E",
          "정답률"
        );
        const pieConfig2 = doughnutConfigWithCenter(
          latestData.passRate,
          "#5DC5C6",
          "통과자"
        );
        const pieConfig3 = doughnutConfigWithCenter(
          latestData.attendRate,
          "#3AB54A",
          "응시자"
        );
        charts[1] = new Chart(canvases[1], pieConfig1);
        charts[2] = new Chart(canvases[2], pieConfig2);
        charts[3] = new Chart(canvases[3], pieConfig3);
      });
    });
  }, []);

  return (
    <div id="classmain_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar class_id={class_id} selected="main" />
        </React.Fragment>
        <div id="contents_wrapper">
          <div id="result_title"> &#x27A4; {latestData.date} 시험</div>

          <div id="up_graph_container">
            <canvas id="up_canvas" width="800" height="400"></canvas>
          </div>
          <div id="down_graph_container">
            <canvas id="down_canvas_1"></canvas>
            <canvas id="down_canvas_2"></canvas>
            <canvas id="down_canvas_3"></canvas>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ClassMain;
