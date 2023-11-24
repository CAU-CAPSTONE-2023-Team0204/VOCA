import { useNavigate } from "react-router-dom";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import useAuth from "../hooks/useAuth";
import "../styles/class_select.css";
import { useEffect, useState } from "react";

const ClassSelect = () => {
  const axiosPrivate = useAxiosPrivate();
  const [classList, setCLassList] = useState();

  useEffect(() => {
    // Function to fetch data from the server
    const fetchData = async () => {
      try {
        const response = await axiosPrivate.get("/api/classes/me"); // Update state with the data received from the server
        setCLassList(response.data.userClassList);
      } catch (error) {
        // Handle error if the request fails
        console.error("Error fetching data:", error);
      }
    }; // Call the fetchData function when the component mounts
    fetchData();
  }, []); // Empty dependency array ensures this effect runs only once on mount

  //const classList = ["class1", "class2", "class2"];
  const navigate = useNavigate();
  const createButtonHandler = () => {
    navigate("/create_class");
  };
  return (
    <div id="classes_page_container">
      <div id="classes_wrapper">
        <div id="contents_container">
          내 클래스
          <div id="button_list">
            <button id="class_add_button" onClick={createButtonHandler}>
              +
            </button>
            <div id="class_list_container">
              클래스 리스트
              {classList?.length ? (
                <ul id="class_list">
                  {classList.map((classname, i) => (
                    <li className="class_item" key={i}>
                      <a
                        className="class_link"
                        href={`/class/${classname.id}/main`}
                      >
                        {classname.name}
                      </a>
                    </li>
                  ))}
                </ul>
              ) : (
                <p> CREATE A CLASS !</p>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ClassSelect;
