package ir.sharif.sad.specification;

import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

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
        Pattern pattern = Pattern.compile("(\\w+?)([:<>#])(\\w+?),");
        Matcher matcher = pattern.matcher(filter + ",");
        while (matcher.find()){
            this.items.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
    }

    public  <T> Specification getSpecified() {
        if(items.isEmpty())
            return null;
        Specification result = new CustomSpecification<T>(items.get(0));
        List<Specification> collect = items.stream().map(e->Specification.where(result).and(new CustomSpecification(e))).collect(Collectors.toList());
        if(collect.size() != 0)
            return collect.get(0);
        else
            return result;
    }
}
