package com.apple.weatherforecastservice.utils.metrics;

/**
 * Utility class for metrics
 */

public class MetricsUtils {
    private static final String UNDERSCORE_DELIMITIER = "_";
    private static final String EXECUTION_COUNT        = "executionCount";
    private static final String EXPECTED_ERROR = "errorCount" ;
    private static final String UNEXPECTED_ERROR_COUNT  = "unexpectedErrorCount";
    private static final String DOT_DELIMITER          = ".";
    private static final String GAMEID_PREFIX          = "com.glu.";
    private static final String ALL_GAMES              = "all";

    private MetricsUtils() {}

    // new, using timerBuilder - should include _count and _time bundled together
    public static String getMetricsKey(String env, String serviceName, String requestType){
        return buildMetricsKey(serviceName, env, requestType);
    }
    
    public static String getMetricsExecutionCountKey(String env, String serviceName, String requestType) {
    			return buildMetricsKey(serviceName, env, requestType, EXECUTION_COUNT);
	}

    public static String getMetricsExpectedErrorKey(String env, String serviceName, String requestType) {
        return buildMetricsKey(serviceName, env, requestType, EXPECTED_ERROR);
    }

    public static String getMetricsUnexpectedErrorCountKey(String env, String gameId, String serviceName, String requestType, Throwable error) {
        return getMetricsUnexpectedErrorCountKey(env, gameId, serviceName, requestType, error.getClass().getSimpleName());
    }

    public static String getMetricsUnexpectedErrorCountKey(String env, String gameId, String serviceName, String requestType, String cause) {
        return buildMetricsKey(env, serviceName, getShortGameName(gameId), requestType, cause, UNEXPECTED_ERROR_COUNT);
    }

    /**
     * Get short name from game id
     * eg: For gameId = com.glu.test, return test
     *
     * @param gameId : the game identifier
     * @return       : short name from gameId
     */
    public static String getShortGameName(String gameId){
        if (gameId == null){
            return ALL_GAMES;
        }

        String shortGameName = gameId;
        int gameIdPrefixIndex = gameId.indexOf(GAMEID_PREFIX);
        if (gameIdPrefixIndex >= 0) {
            shortGameName = gameId.substring(GAMEID_PREFIX.length());
        }
        shortGameName = shortGameName.replaceAll("\\.", "");
        return shortGameName;
    }

    private static String buildMetricsKey(String... parts){
        final StringBuilder sb = new StringBuilder();
        String extra = "";
        for (String part : parts) {
            sb.append(extra);
            sb.append(part);
            extra = UNDERSCORE_DELIMITIER;
        }
        return sb.toString();
    }

}
