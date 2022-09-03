import { baseUserUrl, baseDealerUrl } from "@/contants/urls";
import axios from "axios";
import { getToken } from "@/utils/storage.js";

axios.defaults.headers.common["Authorization"] = getToken();

export const getAllProductFromAdmin = ({
  searchText,
  successCallback,
  errorCallback,
}) => {
  axios
    .get(
      `${baseUserUrl}/inventory/detail/search?page=0&query=${searchText}&size=10`
    )
    .then((res) => {
      successCallback && successCallback(res);
    })
    .catch((err) => {
      errorCallback && errorCallback(err);
    });
};

export const getAllProductByDealerId = ({
  dealerId,
  successCallback,
  errorCallback,
}) => {
  axios
    .get(`${baseDealerUrl}/dealer/id?id=${dealerId}&page=0&size=10`)
    .then((res) => {
      successCallback && successCallback(res);
    })
    .catch((err) => {
      errorCallback && errorCallback(err);
    });
};

export const getAllRequestHistory = ({
  dealerId,
  successCallback,
  errorCallback,
}) => {
  axios
    .get(`${baseUserUrl}/stock/history/${dealerId}`)
    .then((res) => {
      successCallback && successCallback(res);
    })
    .catch((err) => {
      errorCallback && errorCallback(err);
    });
};

export const requestStock = ({
  requestData,
  successCallback,
  errorCallback,
}) => {
  axios
    .post(`${baseUserUrl}/stock/admin`, requestData)
    .then((res) => {
      successCallback && successCallback(res);
    })
    .catch((err) => {
      errorCallback && errorCallback(err);
    });
};
