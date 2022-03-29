# See
# https://github.com/intuit/karate#syntax-guide
# for how to write feature scenarios
Feature: Retrieve Git starred repositories created in the last 7 days

  Scenario: Get 5 top starred repositories
    Given url microserviceUrl
    Given param count = '1'
    And path '/git/starredrepos'
    When method GET
    Then status 200
    And match header Content-Type contains 'application/json'
    And match response ==
 """
	 { items: [{
		"name" : '#string',
		"html_url" : '#string',
		"description" : '##string',
		"watchers_count" : '#number',
		"language" : '##string'
	  }]
	 }
  """

  Scenario: Get error response when invalid API parameter passed
    Given url microserviceUrl
    Given param count = 'something'
    And path '/git/starredrepos'
    When method GET
    Then status 400
    And match header Content-Type contains 'application/json'
    And match response ==
 """
	{
	   "message" : '#string',
	   "status" : "BAD_REQUEST",
	   "timestamp" : '#string'
	}
  """
