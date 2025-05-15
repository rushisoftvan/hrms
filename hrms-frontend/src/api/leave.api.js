import { request } from '../utils/RequestWrapper';

export const LeaveListUrl = 'http://localhost:8087/api/hrms/leave/leave-request-details';
export const fetchLeaveTypeUrl = 'http://localhost:8087/api/hrms/leave/fetch-leaveType';
export const applyLeaveTypeUrl = 'http://localhost:8087/api/hrms/leave/apply-leave';
export function fetchLeaveRequest(getLeaveRequest) {
  return request({
    url: LeaveListUrl,
    method: 'POST',
    headers: {
      "Content-Type": "application/json",
      "userId": 1,  // Pass userId dynamically
    },
    body: JSON.stringify(getLeaveRequest)
  });

}

  export function fetchLeaveType() {
    return request({
      url: fetchLeaveTypeUrl,
      method: 'GET',
    });
  }

  export function applyLeave(leaveApplyRequest){

  return request({
    url: applyLeaveTypeUrl,
    method: 'POST',
    headers: {
      "Content-Type": "application/json",
      "userId": 2,  // Pass userId dynamically
    },
    body: JSON.stringify(leaveApplyRequest)
  });
}

