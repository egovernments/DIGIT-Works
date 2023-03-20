package org.egov.wms.repository.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.wms.util.MDMSUtil;
import org.egov.wms.web.model.WMSSearchRequest;
import org.egov.wms.web.model.V2.SearchQueryConfiguration;
import org.egov.wms.web.model.V2.SearchParam;
import org.egov.wms.web.model.V2.SortParam;
import org.egov.wms.web.model.workflow.ProcessInstanceSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

import static org.egov.wms.util.WMSConstants.*;


@Slf4j
@Component
public class WMSSearchQueryBuilder implements QueryBuilderInterface {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MDMSUtil mdmsUtil;


    @Override
    public Map<String, Object> getESQuery(WMSSearchRequest wmsSearchRequest, Boolean isPaginationRequired, String module) {

        SearchQueryConfiguration configuration = mdmsUtil.getConfigFromMDMS(wmsSearchRequest,module);
        Map<String, Object> params = wmsSearchRequest.getInbox().getModuleSearchCriteria();
        Map<String, Object> baseEsQuery = getBaseESQueryBody(wmsSearchRequest, isPaginationRequired);

        if(isPaginationRequired) {
            // Adds sort clause to the inbox ES query only in case pagination is present, else not
            String sortClauseFieldPath = configuration.getSortParam().getPath();
            SortParam.Order sortOrder = wmsSearchRequest.getInbox().getModuleSearchCriteria().containsKey(SORT_ORDER_CONSTANT) ? SortParam.Order.valueOf((String) wmsSearchRequest.getInbox().getModuleSearchCriteria().get(SORT_ORDER_CONSTANT)) : configuration.getSortParam().getOrder();
            addSortClauseToBaseQuery(baseEsQuery, sortClauseFieldPath, sortOrder);

            // Adds source filter only when requesting for inbox items.
            List<String> sourceFilterPathList = configuration.getSourceFilterPathList();
            addSourceFilterToBaseQuery(baseEsQuery, sourceFilterPathList);
        }

        Map<String, Object> innerBoolClause = (HashMap<String, Object>) ((HashMap<String, Object>) baseEsQuery.get(QUERY_KEY)).get(BOOL_KEY);
        List<Object> mustClauseList = (ArrayList<Object>) innerBoolClause.get(MUST_KEY);

        Map<String, String> nameToPathMap = new HashMap<>();
        Map<String, SearchParam.Operator> nameToOperator = new HashMap<>();

        configuration.getAllowedSearchCriteria().forEach(searchParam -> {
            nameToPathMap.put(searchParam.getName(), searchParam.getPath());
            nameToOperator.put(searchParam.getName(), searchParam.getOperator());
        });

        addModuleSearchCriteriaToBaseQuery(params, nameToPathMap, nameToOperator, mustClauseList);
        if (wmsSearchRequest.getInbox().getProcessSearchCriteria() != null) {
            addProcessSearchCriteriaToBaseQuery(wmsSearchRequest.getInbox().getProcessSearchCriteria(), nameToPathMap, nameToOperator, mustClauseList);
        }

        innerBoolClause.put(MUST_KEY, mustClauseList);

        return baseEsQuery;
    }

    private void addSourceFilterToBaseQuery(Map<String, Object> baseEsQuery, List<String> sourceFilterPathList) {
        if(!CollectionUtils.isEmpty(sourceFilterPathList))
            baseEsQuery.put(SOURCE_KEY, sourceFilterPathList);
    }

    private void addSortClauseToBaseQuery(Map<String, Object> baseEsQuery, String sortClauseFieldPath, SortParam.Order sortOrder) {
        List<Map> sortClause = new ArrayList<>();
        Map<String, Object> innerSortOrderClause = new HashMap<>();
        innerSortOrderClause.put(ORDER_KEY, sortOrder);
        Map<String, Map> outerSortClauseChild = new HashMap<>();
        outerSortClauseChild.put(sortClauseFieldPath, innerSortOrderClause);
        sortClause.add(outerSortClauseChild);
        baseEsQuery.put(SORT_KEY, sortClause);
    }

    private void addProcessSearchCriteriaToBaseQuery(ProcessInstanceSearchCriteria processSearchCriteria, Map<String, String> nameToPathMap, Map<String, SearchParam.Operator> nameToOperator, List<Object> mustClauseList) {
        if(!ObjectUtils.isEmpty(processSearchCriteria.getTenantId())){
            String key = "tenantId";
            Map<String, Object> mustClauseChild = null;
            Map<String, Object> params = new HashMap<>();
            params.put(key, processSearchCriteria.getTenantId());
            mustClauseChild = (Map<String, Object>) prepareMustClauseChild(params, key, nameToPathMap, nameToOperator);
            if(CollectionUtils.isEmpty(mustClauseChild)){
                log.info("Error occurred while preparing filter for must clause. Filter for key " + key + " will not be added.");
            }else {
                mustClauseList.add(mustClauseChild);
            }
        }

        if(!ObjectUtils.isEmpty(processSearchCriteria.getStatus())){
            String key = "status";
            Map<String, Object> mustClauseChild = null;
            Map<String, Object> params = new HashMap<>();
            params.put(key, processSearchCriteria.getStatus());
            mustClauseChild = (Map<String, Object>) prepareMustClauseChild(params, key, nameToPathMap, nameToOperator);
            if(CollectionUtils.isEmpty(mustClauseChild)){
                log.info("Error occurred while preparing filter for must clause. Filter for key " + key + " will not be added.");
            }else {
                mustClauseList.add(mustClauseChild);
            }
        }

        if(!ObjectUtils.isEmpty(processSearchCriteria.getAssignee())){
            String key = "assignee";
            Map<String, Object> mustClauseChild = null;
            Map<String, Object> params = new HashMap<>();
            params.put(key, processSearchCriteria.getAssignee());
            mustClauseChild = (Map<String, Object>) prepareMustClauseChild(params, key, nameToPathMap, nameToOperator);
            if(CollectionUtils.isEmpty(mustClauseChild)){
                log.info("Error occurred while preparing filter for must clause. Filter for key " + key + " will not be added.");
            }else {
                mustClauseList.add(mustClauseChild);
            }
        }

        if(!ObjectUtils.isEmpty(processSearchCriteria.getFromDate())){
            String key = "fromDate";
            Map<String, Object> mustClauseChild = null;
            Map<String, Object> params = new HashMap<>();
            params.put(key, processSearchCriteria.getFromDate());
            mustClauseChild = (Map<String, Object>) prepareMustClauseChild(params, key, nameToPathMap, nameToOperator);
            if(CollectionUtils.isEmpty(mustClauseChild)){
                log.info("Error occurred while preparing filter for must clause. Filter for key " + key + " will not be added.");
            }else {
                mustClauseList.add(mustClauseChild);
            }
        }

        if(!ObjectUtils.isEmpty(processSearchCriteria.getToDate())){
            String key = "toDate";
            Map<String, Object> mustClauseChild = null;
            Map<String, Object> params = new HashMap<>();
            params.put(key, processSearchCriteria.getToDate());
            mustClauseChild = (Map<String, Object>) prepareMustClauseChild(params, key, nameToPathMap, nameToOperator);
            if(CollectionUtils.isEmpty(mustClauseChild)){
                log.info("Error occurred while preparing filter for must clause. Filter for key " + key + " will not be added.");
            }else {
                mustClauseList.add(mustClauseChild);
            }
        }

    }


    private void addModuleSearchCriteriaToBaseQuery(Map<String, Object> params, Map<String, String> nameToPathMap, Map<String, SearchParam.Operator> nameToOperator, List<Object> mustClauseList) {
        params.keySet().forEach(key -> {
            if(!(key.equals(SORT_ORDER_CONSTANT) || key.equals(SORT_BY_CONSTANT) || key.equals("moduleName"))) {
                Map<String, Object> mustClauseChild = null;

                //if the operator is 'IN' convert the moduleSearchCriteria param value as list
                SearchParam.Operator operator = nameToOperator.get(key);
                if (SearchParam.Operator.IN == operator && !(params.get(key) instanceof List)) {
                    List<String> paramsValueList = Arrays.asList(params.get(key).toString().split(","));
                    params.put(key,paramsValueList);
                }

                mustClauseChild = (Map<String, Object>) prepareMustClauseChild(params, key, nameToPathMap, nameToOperator);
                if (CollectionUtils.isEmpty(mustClauseChild)) {
                    log.info("Error occurred while preparing filter for must clause. Filter for key " + key + " will not be added.");
                } else {
                    mustClauseList.add(mustClauseChild);
                }
            }
        });
    }

    @Override
    public Map<String, Object> getStatusCountQuery(WMSSearchRequest wmsSearchRequest) {
        Map<String, Object> baseEsQuery = getBaseESQueryBody(wmsSearchRequest, Boolean.FALSE);
        appendStatusCountAggsNode(baseEsQuery);
        return baseEsQuery;
    }

    @Override
    public Map<String, Object> getNearingSlaCountQuery(WMSSearchRequest wmsSearchRequest, Long businessServiceSla, String module) {
        Map<String, Object> baseEsQuery = getESQuery(wmsSearchRequest, Boolean.FALSE,module);
        Long currenTimeInMillis = System.currentTimeMillis();
        Long lteParam = currenTimeInMillis;
        Long slotLimit = businessServiceSla - 40 * (businessServiceSla/100);
        Long gteParam = currenTimeInMillis - slotLimit;

        appendNearingSlaCountClause(baseEsQuery, gteParam, lteParam);
        return baseEsQuery;
    }

    private void appendNearingSlaCountClause(Map<String, Object> baseEsQuery, Long gteParam, Long lteParam) {
        List mustClause = JsonPath.read(baseEsQuery, "$.query.bool.must");
        Map<String, Object> rangeObject = new HashMap<>();
        Map<String, Object> rangeClause = new HashMap<>();
        rangeClause.put("gte", gteParam);
        rangeClause.put("lte", lteParam);
        rangeObject.put("Data.auditDetails.lastModifiedTime", rangeClause);
        HashMap<String, Object> rangeMap = new HashMap<>();
        rangeMap.put("range", rangeObject);
        mustClause.add(rangeMap);
    }

    private void appendStatusCountAggsNode(Map<String, Object> baseEsQuery) {
        Map<String, Object> aggsNode = new HashMap<>();
        aggsNode.put("statusCount", new HashMap<>());
        Map<String, Object> statusCountNode = (Map<String, Object>) aggsNode.get("statusCount");
        statusCountNode.put("terms", new HashMap<>());
        Map<String, Object> innerTermsQuery = (Map<String, Object>) statusCountNode.get("terms");
        innerTermsQuery.put("field", "Data.currentProcessInstance.state.uuid.keyword");
        baseEsQuery.put("aggs", aggsNode);
    }

    private Map<String, Object> getBaseESQueryBody(WMSSearchRequest wmsSearchRequest, Boolean isPaginationRequired){
        Map<String, Object> baseEsQuery = new HashMap<>();
        Map<String, Object> boolQuery = new HashMap<>();
        Map<String, Object> mustClause = new HashMap<>();

        // Prepare bool query
        boolQuery.put("bool", new HashMap<>());
        Map<String, Object> innerBoolBody = (Map<String, Object>) boolQuery.get("bool");
        innerBoolBody.put("must", new ArrayList<>());

        // Prepare base ES query
        if(isPaginationRequired) {
            baseEsQuery.put("from", wmsSearchRequest.getInbox().getOffset());
            baseEsQuery.put("size", wmsSearchRequest.getInbox().getLimit());
        }
        baseEsQuery.put("query", boolQuery);

        return baseEsQuery;
    }

    private Object prepareMustClauseChild(Map<String, Object> params, String key, Map<String, String> nameToPathMap, Map<String, SearchParam.Operator> nameToOperatorMap){

        SearchParam.Operator operator = nameToOperatorMap.get(key);
        if(operator == null || operator.equals(SearchParam.Operator.EQUAL) || SearchParam.Operator.IN == operator){
            // Add terms clause in case the search criteria has a list of values
            if(params.get(key) instanceof List){
                Map<String, Object> termsClause = new HashMap<>();
                termsClause.put("terms", new HashMap<>());
                Map<String, Object> innerTermsClause = (Map<String, Object>) termsClause.get("terms");
                innerTermsClause.put(addDataPathToSearchParamKey(key, nameToPathMap), params.get(key));
                return termsClause;
            }
            // Add term clause in case the search criteria has a single value
            else{
                Map<String, Object> termClause = new HashMap<>();
                termClause.put("term", new HashMap<>());
                Map<String, Object> innerTermClause = (Map<String, Object>) termClause.get("term");
                innerTermClause.put(addDataPathToSearchParamKey(key, nameToPathMap), params.get(key));
                return termClause;
            }
        }
        else if (SearchParam.Operator.FUZZY == operator) {
            Map<String, Object> matchClause = new HashMap<>();
            matchClause.put("match", new HashMap<>());
            Map<String, Object> innerMatchClause = (Map<String, Object>) matchClause.get("match");
            innerMatchClause.put(addDataPathToSearchParamText(key, nameToPathMap), new HashMap<>());
            Map<String, Object> innermostMatchClause = (Map<String, Object>) innerMatchClause.get(addDataPathToSearchParamText(key, nameToPathMap));
            innermostMatchClause.put("query",params.get(key));
            innermostMatchClause.put("fuzziness","AUTO");
            return matchClause;
        }
        else {
            Map<String, Object> rangeClause = new HashMap<>();
            rangeClause.put("range", new HashMap<>());
            Map<String, Object> innerTermClause = (Map<String, Object>) rangeClause.get("range");
            Map<String, Object> comparatorMap = new HashMap<>();

            if (operator.equals(SearchParam.Operator.LTE)){
                comparatorMap.put("lte",params.get(key));
            }
            else if (operator.equals(SearchParam.Operator.GTE)){
                comparatorMap.put("gte",params.get(key));
            }
            innerTermClause.put(addDataPathToSearchParamKey(key, nameToPathMap), comparatorMap);
            return rangeClause;
        }

    }

    private String addDataPathToSearchParamKey(String key, Map<String, String> nameToPathMap){

        String path = nameToPathMap.get(key);

        if (StringUtils.isEmpty(path))
            path = "Data." + key + ".keyword";

        return path;
    }

    private String addDataPathToSearchParamText(String key, Map<String, String> nameToPathMap){

        String path = nameToPathMap.get(key);

        if (StringUtils.isEmpty(path))
            path = "Data." + key;

        return path;
    }

}
