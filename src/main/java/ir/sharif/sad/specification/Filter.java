package ir.sharif.sad.specification;

import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Data
public class Filter {
    private List<SearchCriteria> items;

    public Filter(String filter) {
        this.items = new LinkedList<>();
        Pattern pattern = Pattern.compile("(\\w+?)(==|>=|<=|:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(filter + ",");
        while (matcher.find()){
            this.items.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        System.out.println("hi");
    }

    public  <T extends Specification> Specification getSpecified(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<T> constructor = clazz.getConstructor(SearchCriteria.class);
        Specification result = constructor.newInstance(items.remove(0));
        List<Specification> collect = items.stream().map(e -> {
            try {
                return Specification.where(result).and(constructor.newInstance(e));
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        if(collect.size() != 0)
            return collect.get(0);
        else
            return result;
    }
}
