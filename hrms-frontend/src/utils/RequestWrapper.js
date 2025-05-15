
import axios from 'axios';
// import * as auth from 'app/store/ducks/auth.duck';

//const dispatch = useDispatch();
export function setupAxios(axios, store) {
//export const setupAxios = withRouter(({ axios, store }) => {
  //const dispatch = useDispatch();

  // const handleURL = (url) => {
  //   //dispatching the action
  //   dispatch(auth.actions.setRedirectURL(url))
  // };
  axios.interceptors.request.use(
    (config) => {
      // const {
      //   auth: {accessToken}
      // } = store.getState();
      const  accessToken = localStorage.getItem("AccessTKN");

      if (accessToken) {
        config.headers.Authorization = `Bearer ${accessToken}`;
      }
      return config;
    },
    (err) => Promise.reject(err)
  );
  axios.interceptors.response.use(
    (response) => {
      if (response.config.headers && response.headers.authorization && !response.config.url.includes("notification/totalUnread")) {
        localStorage.setItem("AccessTKN",response.headers.authorization)
        setLogoutTime();
        window.addEventListener('mousemove',()=>{
          setLogoutTime();
        })
        window.addEventListener('keydown',()=>{
          setLogoutTime();
        })

        store.dispatch(
          //auth.actions.setAccessToken(response.headers.authorization)
          auth.actions.setAccessToken(localStorage.getItem("AccessTKN"))
        );
      }
      response = response.data ? response.data : response;
      return response;
    },
    (error) => {
      let response = {
        status: false,
        message: apiEnums.SOMETHING_WRONG
      };
      if (error && error.response) {
        if (error && error.response && error.response.status === 401) {
          if (window.location.pathname.endsWith("/auth/login")) {
            response = error.response.data ? error.response.data : response;
            throw response;
          } else {
            let url = window.location.href.split("/nester")[1];
            // {handleURL(url)}
            //dispatch(auth.actions.setRedirectURL(url));
            window.location.href =
              process.env.PUBLIC_URL + "/logout?redirectUrl_=" + url;
          }
        } else if (error && error.response && error.response.status === 403) {
          // handle403Page(appConfigs.AUTH_BASE_URL);
          window.location.reload(true);
        } else if (error.response.data && error.response.data.message) {
          response = error.response.data ? error.response.data : response;
          throw response;
        } else {
          throw response;
        }
      } else if (
        error &&
        error.toString() &&
        error.toString().includes("Network Error")
      ) {
        window.location.href =
          process.env.PUBLIC_URL + "/logout";
      } else {
        throw response;
      }
    }
  );
}
//});

export const request = (options) => {
  console.log('request');
  const config = {
    url: options["url"],
    method: options["method"],
    headers: {
      "Content-Type": "application/json",  // Default headers
      ...options["headers"],  // Include additional headers dynamically
    },
  };

  if (options["body"]) {
    config["data"] = options["body"];
  }
  if (options["params"]) {
    config["params"] = options["params"];
  }
  if (navigator.onLine) {
    return axios.request(config);
    // .then((response) => {
    //   return response.data;
    // })
    // .catch((error) => {
    //   // let response;
    //   return error;
    // });
  } else {
    let response;
    response = {
      status: false,
      message: apiEnums.INTERNET_DISCONNECTED
    };
    return response;
  }
};
let setLogoutTime=()=>{
  localStorage.setItem('AccessLogOut',new Date().getTime()+900000)
}
// Check every 30 second is user is active or not in any tab?
setInterval(()=>{
  let timeout= localStorage.getItem('AccessLogOut')
  if(timeout && timeout-new Date().getTime()<1){
    localStorage.clear();
    window.location.reload();
  }
},30000)