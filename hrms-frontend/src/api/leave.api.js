import { request } from '../utils/RequestWrapper';

export const LeaveListUrl = 'http://localhost:8087/api/hrms/leave/leave-request-details';

export function fetchLeaveRequest(getLeaveRequest) {
  return request({
    url: LeaveListUrl,
    method: 'POST',
    headers: {
      "Content-Type": "application/json",
      "userId": 1,  // Pass userId dynamically
    },
    body:   JSON.stringify(getLeaveRequest)
});
}

