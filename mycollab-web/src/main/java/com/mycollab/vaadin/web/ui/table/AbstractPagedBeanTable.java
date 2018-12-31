/**
 * Copyright © MyCollab
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycollab.vaadin.web.ui.table;

import com.mycollab.common.TableViewField;
import com.mycollab.common.domain.CustomViewStore;
import com.mycollab.common.domain.NullCustomViewStore;
import com.mycollab.common.json.FieldDefAnalyzer;
import com.mycollab.common.service.CustomViewStoreService;
import com.mycollab.db.arguments.BasicSearchRequest;
import com.mycollab.db.arguments.SearchCriteria;
import com.mycollab.spring.AppContextUtil;
import com.mycollab.vaadin.AppUI;
import com.mycollab.vaadin.UserUIContext;
import com.mycollab.vaadin.event.PageableHandler;
import com.mycollab.vaadin.event.SelectableItemHandler;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.data.Container;
import com.vaadin.v7.data.util.BeanItem;
import com.vaadin.v7.data.util.BeanItemContainer;
import com.vaadin.v7.ui.Table;
import com.vaadin.v7.ui.Table.ColumnGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import java.util.*;

import static com.mycollab.vaadin.web.ui.WebThemes.SCROLLABLE_CONTAINER;

/**
 * @param <S>
 * @param <B>
 * @author MyCollab Ltd.
 * @since 2.0
 */
public abstract class AbstractPagedBeanTable<S extends SearchCriteria, B> extends VerticalLayout implements IPagedTable<S, B> {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(AbstractPagedBeanTable.class);

    private int displayNumItems = BasicSearchRequest.DEFAULT_NUMBER_SEARCH_ITEMS;
    private Collection<B> currentListData;
    protected BasicSearchRequest<S> searchRequest;

    private boolean isAscending = true;
    private Object sortColumnId;

    private int currentPage = 1;
    private int totalPage = 1;
    private int currentViewCount;
    protected int totalCount;

    protected Table tableItem;
    private HorizontalLayout controlBarWrapper;

    private Set<SelectableItemHandler<B>> selectableHandlers = new HashSet<>();
    private Set<PageableHandler> pageableHandlers = new HashSet<>();

    protected Class<B> type;

    private TableViewField requiredColumn;
    private Set<TableViewField> displayColumns;
    private Set<TableViewField> defaultSelectedColumns;

    private final Map<Object, ColumnGenerator> columnGenerators = new HashMap<>();

    public AbstractPagedBeanTable(Class<B> type, Set<TableViewField> displayColumns) {
        this(type, null, displayColumns);
    }

    public AbstractPagedBeanTable(Class<B> type, TableViewField requiredColumn, Set<TableViewField> displayColumns) {
        this(type, null, requiredColumn, displayColumns);
    }

    public AbstractPagedBeanTable(Class<B> type, String viewId, TableViewField requiredColumn, Set<TableViewField> displayColumns) {
        this.setMargin(false);
        if (viewId != null) {
            CustomViewStoreService customViewStoreService = AppContextUtil.getSpringBean(CustomViewStoreService.class);
            CustomViewStore viewLayoutDef = customViewStoreService.getViewLayoutDef(AppUI.getAccountId(),
                    UserUIContext.getUsername(), viewId);
            if (!(viewLayoutDef instanceof NullCustomViewStore)) {
                try {
                    this.displayColumns = FieldDefAnalyzer.toTableFields(viewLayoutDef.getViewinfo());
                } catch (Exception e) {
                    LOG.error("Error", e);
                    this.displayColumns = displayColumns;
                }
            } else {
                this.displayColumns = displayColumns;
            }
        } else {
            this.displayColumns = displayColumns;
        }

        this.defaultSelectedColumns = displayColumns;
        this.requiredColumn = requiredColumn;
        this.type = type;
        addStyleName(SCROLLABLE_CONTAINER);
    }

    public void setDisplayColumns(Set<TableViewField> viewFields) {
        this.displayColumns = viewFields;
        displayTableColumns();
        this.markAsDirty();
    }

    private void displayTableColumns() {
        Set<String> visibleColumnsCol = new LinkedHashSet<>();
        Set<String> columnHeadersCol = new LinkedHashSet<>();

        if (requiredColumn != null) {
            visibleColumnsCol.add(requiredColumn.getField());
            columnHeadersCol.add(UserUIContext.getMessage(requiredColumn.getDescKey()));
            tableItem.setColumnWidth(requiredColumn.getField(), requiredColumn.getDefaultWidth());
        }

        displayColumns.forEach(viewField -> {
            visibleColumnsCol.add(viewField.getField());
            columnHeadersCol.add(UserUIContext.getMessage(viewField.getDescKey()));
        });

//        for (int i = 0; i < displayColumns.size(); i++) {
//            TableViewField viewField = displayColumns.get(i);
//            visibleColumnsCol.add(viewField.getField());
//            columnHeadersCol.add(UserUIContext.getMessage(viewField.getDescKey()));
//
//            if (i == 0) {
//                tableItem.setColumnExpandRatio(viewField.getField(), 1.0f);
//            } else {
//                tableItem.setColumnWidth(viewField.getField(), viewField.getDefaultWidth());
//            }
//        }

        String[] visibleColumns = visibleColumnsCol.toArray(new String[visibleColumnsCol.size()]);
        String[] columnHeaders = columnHeadersCol.toArray(new String[columnHeadersCol.size()]);

        tableItem.setVisibleColumns(visibleColumns);
        tableItem.setColumnHeaders(columnHeaders);
    }

    @Override
    public void addSelectableItemHandler(final SelectableItemHandler<B> handler) {
        selectableHandlers.add(handler);
    }

    @Override
    public int currentViewCount() {
        return this.currentViewCount;
    }


    @Override
    public int totalItemsCount() {
        return totalCount;
    }

    @Override
    public void addPageableHandler(final PageableHandler handler) {
        pageableHandlers.add(handler);
    }

    @Override
    public Collection<B> getCurrentDataList() {
        return currentListData;
    }

    public void setCurrentDataList(Collection<B> list) {
        currentListData = list;
        currentViewCount = list.size();
        createTable();
    }

    @Override
    public void addTableListener(TableClickListener listener) {
        addListener(TableClickEvent.TABLE_CLICK_IDENTIFIER, TableClickEvent.class, listener, TableClickListener.itemClickMethod);
    }

    protected void fireTableEvent(TableClickEvent event) {
        fireEvent(event);
    }

    @Override
    public void addGeneratedColumn(Object id, ColumnGenerator generatedColumn) {
        this.columnGenerators.put(id, generatedColumn);
    }

    @Override
    public int setSearchCriteria(final S searchCriteria) {
        searchRequest = new BasicSearchRequest<>(searchCriteria, currentPage, displayNumItems);
        doSearch();
        return totalCount;
    }

    public void setDisplayNumItems(int displayNumItems) {
        this.displayNumItems = displayNumItems;
    }

    @Override
    public B getBeanByIndex(final Object itemId) {
        final Container container = tableItem.getContainerDataSource();
        final BeanItem<B> item = (BeanItem<B>) container.getItem(itemId);
        return item == null ? null : item.getBean();
    }

    @Override
    public void refresh() {
        doSearch();
    }

    private void pageChange(final int currentPage) {
        if (searchRequest != null) {
            this.currentPage = currentPage;
            searchRequest.setCurrentPage(currentPage);
            doSearch();
            pageableHandlers.forEach(handler -> handler.move(currentPage));
        }
    }

    public void fireSelectItemEvent(final B item) {
        selectableHandlers.forEach(handler -> handler.onSelect(item));
    }

    private ComponentContainer createPagingControls() {
        controlBarWrapper = new HorizontalLayout();
        controlBarWrapper.setWidth("100%");
        controlBarWrapper.setStyleName("listControl");

        MHorizontalLayout pageManagement = new MHorizontalLayout();

        // defined layout here ---------------------------

        if (currentPage > 1) {
            MButton firstLink = new MButton("1", clickEvent -> pageChange(1)).withStyleName("buttonPaging");
            pageManagement.addComponent(firstLink);
        }
        if (currentPage >= 5) {
            Label ss1 = new Label("...");
            ss1.addStyleName("buttonPaging");
            pageManagement.addComponent(ss1);
        }

        if (currentPage > 3) {
            MButton previous2 = new MButton("" + (currentPage - 2), clickEvent -> pageChange(currentPage - 2))
                    .withStyleName("buttonPaging");
            pageManagement.addComponent(previous2);
        }
        if (currentPage > 2) {
            MButton previous1 = new MButton("" + (currentPage - 1), clickEvent -> pageChange(currentPage - 1))
                    .withStyleName("buttonPaging");
            pageManagement.addComponent(previous1);
        }
        // Here add current ButtonLinkLegacy
        MButton current = new MButton("" + currentPage, clickEvent -> pageChange(currentPage))
                .withStyleName("buttonPaging", "current");

        pageManagement.addComponent(current);
        final int range = totalPage - currentPage;
        if (range >= 1) {
            MButton next1 = new MButton("" + (currentPage + 1), clickEvent -> pageChange(currentPage + 1))
                    .withStyleName("buttonPaging");
            pageManagement.addComponent(next1);
        }
        if (range >= 2) {
            MButton next2 = new MButton("" + (currentPage + 2), clickEvent -> pageChange(currentPage + 2))
                    .withStyleName("buttonPaging");
            pageManagement.addComponent(next2);
        }
        if (range >= 4) {
            final Label ss2 = new Label("...");
            ss2.addStyleName("buttonPaging");
            pageManagement.addComponent(ss2);
        }
        if (range >= 3) {
            MButton last = new MButton("" + totalPage, clickEvent -> pageChange(totalPage)).withStyleName("buttonPaging");
            pageManagement.addComponent(last);
        }

        pageManagement.setWidth(null);
        controlBarWrapper.addComponent(pageManagement);
        controlBarWrapper.setComponentAlignment(pageManagement, Alignment.MIDDLE_RIGHT);

        return controlBarWrapper;
    }

    abstract protected int queryTotalCount();

    abstract protected List<B> queryCurrentData();

    protected void doSearch() {
        totalCount = this.queryTotalCount();
        totalPage = (totalCount - 1) / searchRequest.getNumberOfItems() + 1;
        if (searchRequest.getCurrentPage() > totalPage) {
            searchRequest.setCurrentPage(totalPage);
        }

        if (totalPage > 1) {
            // Define button layout
            if (controlBarWrapper != null) {
                removeComponent(this.controlBarWrapper);
            }
            this.addComponent(this.createPagingControls());
        } else {
            if (getComponentCount() == 2) {
                removeComponent(getComponent(1));
            }
        }

        currentListData = queryCurrentData();
        currentViewCount = currentListData.size();

        createTable();
    }

    private void createTable() {
        tableItem = new Table();
        tableItem.setWidth("100%");
        tableItem.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        tableItem.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
        tableItem.setSortEnabled(false);

        // set column generator
        for (Map.Entry<Object, ColumnGenerator> entry : columnGenerators.entrySet()) {
            tableItem.addGeneratedColumn(entry.getKey(), entry.getValue());
        }

        if (StringUtils.isNotBlank((String) sortColumnId)) {
            tableItem.setColumnIcon(sortColumnId, isAscending ? VaadinIcons.CARET_DOWN : VaadinIcons.CARET_UP);
        }

        tableItem.addHeaderClickListener(headerClickEvent -> {
            String propertyId = (String) headerClickEvent.getPropertyId();

            if (propertyId.equals("selected")) {
                return;
            }

            if (searchRequest != null) {
                S searchCriteria = searchRequest.getSearchCriteria();
                if (sortColumnId == null) {
                    sortColumnId = propertyId;
                    searchCriteria.setOrderFields(Collections.singletonList(new SearchCriteria.OrderField(propertyId, SearchCriteria.DESC)));
                    isAscending = false;
                } else if (propertyId.equals(sortColumnId)) {
                    isAscending = !isAscending;
                    String direction = (isAscending) ? SearchCriteria.ASC : SearchCriteria.DESC;
                    searchCriteria.setOrderFields(Collections.singletonList(new SearchCriteria.OrderField(propertyId, direction)));
                } else {
                    sortColumnId = propertyId;
                    searchCriteria.setOrderFields(Collections.singletonList(new SearchCriteria.OrderField(propertyId, SearchCriteria.DESC)));
                    isAscending = false;
                }

                setSearchCriteria(searchCriteria);
            }
        });

        BeanItemContainer<B> container = new BeanItemContainer<>(type, currentListData);
        tableItem.setPageLength(0);
        tableItem.setContainerDataSource(container);
        displayTableColumns();

        if (this.getComponentCount() > 0) {
            final Component component0 = this.getComponent(0);
            if (component0 instanceof Table) {
                this.replaceComponent(component0, tableItem);
            } else {
                this.addComponent(tableItem, 0);
            }
        } else {
            this.addComponent(tableItem, 0);
        }
        this.setExpandRatio(tableItem, 1);
    }

    public Table getTable() {
        return tableItem;
    }

    public Set<TableViewField> getDefaultSelectedColumns() {
        return defaultSelectedColumns;
    }

    @Override
    public Set<TableViewField> getDisplayColumns() {
        return displayColumns;
    }

    public Object[] getVisibleColumns() {
        return tableItem.getVisibleColumns();
    }
}