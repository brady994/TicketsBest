package siw.persistence.dao;

import java.util.Map;

import siw.model.AnchestorEventCategory;
import siw.model.EventCategory;

public interface EventCategoryDAO {

    public boolean create(EventCategory modelobject);

    public EventCategory findById(Integer id);

    public EventCategory findByName(String name);

    public Map<Integer, AnchestorEventCategory> showCategory();

    public boolean update(EventCategory modelobject);

    public boolean delete(EventCategory id);
}
