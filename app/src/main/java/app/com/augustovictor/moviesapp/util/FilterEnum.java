package app.com.augustovictor.moviesapp.util;

/**
 * Created by victoraweb on 6/9/16.
 */
public enum FilterEnum {
    POPULARITY("popularity.desc"),
    VOTE_AVARAGE("vote_average.desc");

    private final String name;

    private FilterEnum(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
