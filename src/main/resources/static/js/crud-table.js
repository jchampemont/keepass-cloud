Vue.component('crud-table', {
    props: ["endpoint"],
    data: function() {
       return {
           rows: []
       }
    },
    methods: {
        add: function(row) {
            this.rows.push(row);
        },
        remove: function(id) {
            axios
                .delete('/api/' + this.endpoint + '/' + id)
                .then(response => {
                    this.rows = this.rows.filter(v => (v.id !== id));
                });
        }
    },
    template: `
        <div>
            <b-table :data="rows">
                <template slot-scope="props">
                    <b-table-column field="id" label="ID" sortable>
                        {{ props.row.id }}
                    </b-table-column>
                    <b-table-column field="name" label="Nom" sortable>
                        {{ props.row.name }}
                    </b-table-column>
                    <b-table-column field="created" label="Date de creation" sortable>
                        <span class="tag is-success">
                            {{ new Date(props.row.created).toLocaleDateString() }} à {{ new Date(props.row.created).toLocaleTimeString() }}
                        </span>
                    </b-table-column>
                    <b-table-column field="modified" label="Date de dernière modification" sortable>
                        <span class="tag is-success" v-if="props.row.modified">
                            {{ new Date(props.row.modified).toLocaleDateString() }} à {{ new Date(props.row.modified).toLocaleTimeString() }}
                        </span>
                    </b-table-column>
                    <b-table-column label="Actions">
                        <button class="button is-primary is-small" type="button" @click="remove(props.row.id)">
                            <b-icon icon="delete" size="is-small"></b-icon>
                        </button>
                    </b-table-column>
                </template>
            </b-table>
            <hr />
            <crud-modal :endpoint="endpoint"></crud-modal>
        </div>
    `,
    mounted () {
        axios
            .get('/api/' + this.endpoint)
            .then(response => (this.rows = response.data))
    }
});