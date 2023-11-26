import axios, { axiosPrivate } from "../api/axios";
import useAuth from "./useAuth";

const useRefreshToken = () => {
  const { setAuth } = useAuth();

  const refresh = async () => {
    try {
      const response = await axiosPrivate.post("/auth/reissue", {
        withCredentials: true,
      });
      setAuth((prev) => {
        console.log("PREVIOUS REQUEST?");
        console.log(JSON.stringify(prev));
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
