import axios, { axiosPrivate } from "../api/axios";
import { useEffect } from "react";
import useRefreshToken from "./useRefreshToken";
import useAuth from "./useAuth";

const useAxiosPrivate = () => {
  const refresh = useRefreshToken();
  const { auth } = useAuth();

  useEffect(() => {
    const responseIntercept = axiosPrivate.interceptors.response.use(
      (response) => response,
      async (error) => {
        const prevRequest = error?.config;
        if (
          error?.response?.status === 401 &&
          error?.response?.data.code === "TOKEN-002" &&
          !prevRequest?.sent
        ) {
          prevRequest.sent = true;
          const expired_token = prevRequest.headers.Authorization.slice(7);
          var newAccessToken = await refresh(expired_token);
          //.log("NEW_TOKEN FROM AXIOS PRIVATE", newAccessToken);
          if (!newAccessToken)
            newAccessToken = localStorage.getItem("accessToken");
          prevRequest.headers["Authorization"] = `Bearer ${newAccessToken}`;
          return axiosPrivate(prevRequest);
        }
        return Promise.reject(error);
      }
    );
    const requestIntercept = axiosPrivate.interceptors.request.use(
      (config) => {
        if (!config.headers["Authorization"]) {
          const accessToken = localStorage.getItem("accessToken");
          config.headers["Authorization"] = `Bearer ${accessToken}`;
        }
        return config;
      },
      (error) => Promise.reject(error)
    );
    return () => {
      axiosPrivate.interceptors.request.eject(requestIntercept);
      axiosPrivate.interceptors.response.eject(responseIntercept);
    };
  }, [auth, refresh]);
  axiosPrivate.interceptors.request.use((config) => {
    if (!config.headers["Authorization"]) {
      const accessToken = localStorage.getItem("accessToken");
      config.headers["Authorization"] = `Bearer ${accessToken}`;
    }
    return config;
  });
  return axiosPrivate;
};

export default useAxiosPrivate;
