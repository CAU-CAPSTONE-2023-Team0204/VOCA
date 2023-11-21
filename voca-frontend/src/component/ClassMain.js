import useAxiosPrivate from "../hooks/useAxiosPrivate";
import TeacherSidebar from "./TeacherSidebar";
import NavigationBar from "./NavigationBar";
import React from "react";
import "../styles/class_main.css";
import { Chart, registerables } from "chart.js";
import { barConfig, doughnutConfigWithCenter } from "../api/chart";
import { useEffect, useState } from "react";

const ClassMain = () => {
  Chart.register(...registerables);
  var charts = [];
  useEffect(() => {
    const canvases = [
      document.getElementById("up_canvas"),
      document.getElementById("down_canvas_1"),
      document.getElementById("down_canvas_2"),
      document.getElementById("down_canvas_3"),
    ];

    charts.forEach((chart) => {
      chart.destroy();
    });

    const barConfig1 = barConfig([5, 4, 6, 8, 5, 10, 6]);

    charts[0] = new Chart(canvases[0], barConfig1);

    const pieConfig1 = doughnutConfigWithCenter(90, "#8FC63E", "정답률");
    const pieConfig2 = doughnutConfigWithCenter(70, "#5DC5C6", "통과자");
    const pieConfig3 = doughnutConfigWithCenter(100, "#3AB54A", "응시자");
    charts[1] = new Chart(canvases[1], pieConfig1);
    charts[2] = new Chart(canvases[2], pieConfig2);
    charts[3] = new Chart(canvases[3], pieConfig3);
  }, []);

  return (
    <div id="classmain_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar />
        </React.Fragment>
        <div id="contents_wrapper">
          <div id="result_title"> &#x27A4; 10/23일 시험</div>

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
