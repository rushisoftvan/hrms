import { request } from '../utils/RequestWrapper';


export const HolidayListUrl = "http://localhost:8087/api/hrms/holiday/fetch";

export function fetchHolidayList(getHolidayRequest) {
  localStorage.setItem("userId", 1);
  return request({
    url: HolidayListUrl,
    method: "post",
    body: getHolidayRequest
  });
}