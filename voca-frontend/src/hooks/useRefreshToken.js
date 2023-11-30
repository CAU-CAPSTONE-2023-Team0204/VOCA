import axios from "../api/axios";
import useAuth from "./useAuth";

const useRefreshToken = () => {
  const { auth, setAuth } = useAuth();
  const refresh = async (expired_token) => {
    try {
      const response = await axios.post(
        "/auth/reissue",
        JSON.stringify({ accessToken: expired_token }),
        {
          headers: { "Content-Type": "application/json" },
          withCredentials: true,
        }
      );
      setAuth((prev) => {
        //.log("EXPIRED_TOKEN", expired_token);
        //console.log("NEW TOKEN", response.data.accessToken);
        localStorage.setItem("accessToken", response.data.accessToken);
        return { ...prev, accessToken: response.data.accessToken };
      });
      return response.data.accessToken;
    } catch (err) {
      //console.log(err);
      console.log("UNEXPECTED ERROR");
    }
  };
  return refresh;
};

export default useRefreshToken;
