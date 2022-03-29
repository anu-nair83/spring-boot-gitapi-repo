# Implementation details:
1. Implemented API with URL /git/starredrepos and query parameter count. 
i.e. /git/starredrepos?count=1
2. Implemented Circuit breaker to handle any errors that https://api.github.com may return
3. Implemented global exception handler to handle exceptions (MethodArgumentTypeMismatchException, UnsatisfiedServletRequestParameterException) that may occur in request
4. Quotes endpoint wasn't working. Added TimeLimiter to limit wait to 2 seconds and to make method compatible for TimeLimiter changed return type to CompletableFuture<> return type.
TimeLimiter throws TimeOutException which is handled in the global exception handler. 
 Modified functional test quotes.feature to expect status 400
5. Added functional tests in gitapi.feature
6. Added test cases in files for controller and service layer
 
