package exceptions;

import security.services.RateLimiterService;

@ResponseStatus(status=429 , reason="Too Many Requests")
public class TooManyRequestsException extends BaseException{
    public TooManyRequestsException() {
        super ("Request limit has been reached " +
                "(" + RateLimiterService.getLimitRate() + " requests/" +
                RateLimiterService.getWindow() + ") !");
    }
}
