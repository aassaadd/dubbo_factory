package {{groupid}}.{{name}}_facade.model;

import java.io.Serializable;
import java.sql.*;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@Table(name = "{{entity}}")
public class {{classname}} implements Serializable{

{{#each comments}}

    /**
     * {{Comment}}
     */
    {{#if Key }}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    {{/if}}
    @Column(name = "{{Field}}")
    private {{sql2bean Type}} {{tf Field}};
    /**
     * 获取{{Comment}}
     *
     * @return {{tf Field}} - {{Comment}}
     */
    public {{sql2bean Type}} get{{tf2 Field}}() {
            return {{tf Field}};
    }

    /**
     * 设置{{Comment}}
     *
     * @param {{tf Field}} {{Comment}}
     */
    public void set{{tf2 Field}}({{sql2bean Type}} {{tf Field}}) {
            this.{{tf Field}} = {{tf Field}};
    }

{{/each}}

}