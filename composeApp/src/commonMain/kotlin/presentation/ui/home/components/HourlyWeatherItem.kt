package presentation.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.forecast.Hourly
import kotlinx.datetime.Clock

@Composable
fun ColumnScope.HourlyWeatherItem(
    modifier: Modifier = Modifier,
    hourly: Hourly,
) {
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = domain.Util.formatUnixDate(
            pattern = "H",
            time = Clock.System.now().epochSeconds
        ).toInt()
    )

    Spacer(modifier = Modifier.height(5.dp))

    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Today",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = domain.Util.formatUnixDate("MMMM, dd", Clock.System.now().epochSeconds),
                style = MaterialTheme.typography.bodyMedium,
            )

        }
        HorizontalDivider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        LazyRow(
            state = listState,
            modifier = Modifier.padding(16.dp)
        ) {
            items(hourly.weatherInfo.subList(0, 24)) { infoItem ->
                HourlyWeatherInfoItem(infoItem = infoItem)
            }
        }

    }


}