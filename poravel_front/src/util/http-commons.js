import axios from "axios";

const { VITE_VUE_API_URL } = import.meta.env;

// local vue api axios instance
function localAxios() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
    // headers: {
    //   "Content-Type": "application/json;charset=utf-8",
    // },
  });
  
  instance.defaults.headers.common["Authorization"] = "";
  instance.defaults.headers.post["Content-Type"] = "application/json";
  instance.defaults.headers.put["Content-Type"] = "application/json";
  instance.defaults.headers.delete["Content-Type"] = "application/json";
  
  return instance;
}

function localFileAxios() {
  const instanceFile = axios.create({
      baseURL: VITE_VUE_API_URL,
      headers: {
          "Content-Type": "multipart/form-data",
      },
  });
  return instanceFile;
}

export { localAxios, localFileAxios };
