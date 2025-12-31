package schema

type Link struct {
	Title       *string `json:"title" bson:"title"`
	Description *string `json:"description" bson:"description"`
	URL         *string `json:"url" bson:"url"`
	Slug        *string `json:"slug" bson:"slug"`
}

type LinkRequest struct {
	Data *Link `json:"data"`
}
